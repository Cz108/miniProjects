import pygame
from pygame.locals import *
import myItem
import controlAI
import numpy as np
from controlRL import PolicyGradient

# import tensorflow as tf
# from tensorflow import keras
pygame.font.init()
STAT_FONT = pygame.font.SysFont("comicsans", 50)

RL = PolicyGradient(
    n_actions=2,   # 2output
    n_features=7,  # 7 input
    learning_rate=0.02,
    reward_decay=0.995,
    # output_graph=True,
)
# running_reward = 0

#drawing maps
def mapPaint(pygame, screen, BigCircle, smallCircle):
    screen.fill((255, 255, 255))
    pygame.draw.circle(screen, BigCircle.rc, BigCircle.rp, BigCircle.rr)
    # surface, color, center, radius)
    pygame.draw.circle(screen, smallCircle.rc, smallCircle.rp, smallCircle.rr)
    pygame.draw.rect(screen, (0, 0, 255), Rect((0, 1), (30, 158)))
    # Rect(left, top, width, height) -> Rect

# drawing cars
def carPaint(pygame, screen, cars):
    for i in range(len(cars)):
        car = cars[i]
        pygame.draw.rect(screen, (0, 255, 0), Rect((car.x, car.y), (car.size)))
        pygame.draw.line(screen, (255, 255, 0), (car.x + car.size[0] / 2, car.y + car.size[1] / 2),
                         (car.dis_pos[0], car.dis_pos[1]))
        #line(surface, color, start_pos, end_pos, width) -> Rect

        # pygame.draw.line(screen, (0, 255, 0), (car.x + car.size[0] / 2, car.y + car.size[1] / 2),
        #                  (car.ldis_pos[0], car.ldis_pos[1]))
        # pygame.draw.line(screen, (0, 255, 0), (car.x + car.size[0] / 2, car.y + car.size[1] / 2),
        #                  (car.lldis_pos[0], car.lldis_pos[1]))
        # pygame.draw.line(screen, (0, 255, 0), (car.x + car.size[0] / 2, car.y + car.size[1] / 2),
        #                  (car.rdis_pos[0], car.rdis_pos[1]))
        # pygame.draw.line(screen, (0, 255, 0), (car.x + car.size[0] / 2, car.y + car.size[1] / 2),
        #                  (car.rrdis_pos[0], car.rrdis_pos[1]))


def carPaint_RL(pygame, screen, car):
    pygame.draw.rect(screen, (0, 255, 0), Rect((car.x, car.y), (car.size)))
    pygame.draw.line(screen, (255, 255, 0), (car.x + car.size[0] / 2, car.y + car.size[1] / 2),
                     (car.dis_pos[0], car.dis_pos[1]))

def pick_best_AI(cars, carAis):
    bestindex = 0
    farDistance = 1000

    for i in range(len(cars)):
        if cars[i].goal is True:
            farDistance = cars[i].distance
            bestindex = i
            print("1")
            break
        if cars[i].distance < farDistance:
            farDistance = cars[i].distance
            bestindex = i

    print("current best car distance: ", cars[0].distance)
    bestAI = controlAI.carAI()
    w, b = carAis[bestindex].get_weights()
    bestAI.assign_weights(w, b)
    return bestAI
def getReward_RL(distance, isAlive, time):
    score_distance = 1.5 * (560 - distance)/100
    score_time = 0
    if time < 500:
        score_time = -0.7 * time/100
    elif time >= 500 & time < 800:
        score_time = -0.9 * time/100
    else:
        score_time = -1.5 * time/100

    if isAlive is True:
        score_isALive = 1
    else:
        if time < 30:
            score_isALive = -10000
        else:
            score_isALive = -2000
    score_sum = score_distance + score_isALive + score_time
    # print("score_Disctance: ",score_distance, "score_isALive: ", score_isALive, "score_time: ", score_time,
    #       "score_Sum: ", score_sum)
    return score_sum
    # return 1

def gameStart_by_RL():
    pygame.init()
    bigCircle = myItem.Circle((0,0,0), (0,320), 320)
    # surface, color, center, radius)
    smallCircle = myItem.Circle((0, 255, 255), (0,320), 160)
    # generate cars

    car = myItem.car(2, 560, (15, 15))
    screen = pygame.display.set_mode((480, 640), 0, 32)
        # Clock to smoothly run the car
    clock = pygame.time.Clock()
    n_epoches = 1000
    reward = -20000
    for k in range(n_epoches):
        our_range = 1000
        goal = 0
        # print("No.%d time"%k)
        data_new = np.array([car.r*3, car.v/100, car.dis_wall/300, car.ldis_wall/300,
                                 car.lldis_wall/300, car.rdis_wall/300, car.rrdis_wall/300])
        # counter_i = 0
        for counter in range(our_range):
            # counter_i = counter_i + 1
            for event in pygame.event.get():
                if event.type == QUIT:
                    exit()

            time_passed = clock.tick(); time_passed_seconds = time_passed / 1000. # limit it 0.001second
            # print("K: ", k)
            car.get_dis_to_wall((0, 320), 320, (0, 320), 160)
            car.postion_move(time_passed_seconds)

            data = np.array([car.r*3, car.v/100, car.dis_wall/300, car.ldis_wall/300,
                             car.lldis_wall/300, car.rdis_wall/300, car.rrdis_wall/300])
            data = data.T

            action = RL.choose_action(data_new)

            # cardecesion_RL(v, dir, car):
            cardecesion_RL(action[0], action[1], car)

            car.is_live(smallCircle.rp, smallCircle.rr, bigCircle.rp, bigCircle.rr)
            car.distance = car.y

            distance_reward = car.distance
            alive_reward = car.alive
            reward = getReward_RL(distance_reward, alive_reward, counter)
            RL.store_transition(data_new, action[1], action[0], reward)

            if car.is_goal() is True:
                goal+=1
                print("goal!")
            data_new = data

            mapPaint(pygame, screen, bigCircle, smallCircle)
            carPaint_RL(pygame, screen, car)
            text = STAT_FONT.render("Score: " + str(goal), 1, (255, 0, 255))
            screen.blit(text, (480 - 10 - text.get_width(), 10))
            text = STAT_FONT.render("Generation: " + str(k), 1, (255, 0, 255))
            screen.blit(text, (10, 10))
            pygame.display.update()

            flag = 0
            if car.alive is True:
                flag = 1
            if flag == 0:
                print(counter)
                # calculate running reward
                ep_rs_sum = sum(RL.ep_rs)
                if 'running_reward' not in globals():
                    running_reward = ep_rs_sum
                else:
                    running_reward = running_reward * 0.99 + ep_rs_sum * 0.01
                print("episode:", k+1, "  reward:", int(running_reward))
                vt = RL.learn()  # train
                break
            if counter >= our_range-1:
                print(counter)
                # calculate running reward
                ep_rs_sum = sum(RL.ep_rs)
                if 'running_reward' not in globals():
                    running_reward = ep_rs_sum
                else:
                    running_reward = running_reward * 0.99 + ep_rs_sum * 0.01
                print("episode:", k+1, "  reward:", int(running_reward))
                vt = RL.learn()  # train
                break
        car = myItem.car(2, 560, (15, 15))


def gameStart():
    pygame.init()
    bigCircle = myItem.Circle((0,0,0), (0,320), 320)
    # surface, color, center, radius)
    smallCircle = myItem.Circle((0, 255, 255), (0,320), 160)
    # generate cars
    carnum = 100
    cars = []
    carAis = []
    for i in range(carnum):
        cars.append(myItem.car(2, 560, (15,15)))
        carAis.append(controlAI.carAI())
    screen = pygame.display.set_mode((480, 640), 0, 32)
        # Clock to smoothly run the car
    clock = pygame.time.Clock()
    n_epoches = 60
    for k in range(n_epoches):
        goal = 0
        print("No.%d time"%k)
        for i in range(500):
            for event in pygame.event.get():
                if event.type == QUIT:
                    exit()

            time_passed = clock.tick(); time_passed_seconds = time_passed / 1000. # limit it 0.001second
            for i in range(len(cars)):
                car = cars[i]
                if car.alive is False:
                    continue
                car.get_dis_to_wall((0, 320), 320, (0, 320), 160)
                car.postion_move(time_passed_seconds)
                carAi = carAis[i]
                data = np.array([car.r*3, car.v/100, car.dis_wall/300, car.ldis_wall/300,
                                 car.lldis_wall/300, car.rdis_wall/300, car.rrdis_wall/300])

                data = data.T
                v, dir = carAi.forward(data)

                cardecesion(v, dir, car)

                if car.is_goal() is True:
                    goal+=1
                    print("goal!")
                car.is_live(smallCircle.rp, smallCircle.rr, bigCircle.rp, bigCircle.rr)
                car.distance = car.y

            mapPaint(pygame, screen, bigCircle, smallCircle)
            carPaint(pygame, screen, cars)
            text = STAT_FONT.render("Score: " + str(goal), 1, (255, 0, 255))
            screen.blit(text, (480 - 10 - text.get_width(), 10))
            text = STAT_FONT.render("Generation: " + str(k), 1, (255, 0, 255))
            screen.blit(text, (10, 10))
            pygame.display.update()
                # print("Asd")
                # v, dir = carAi.forward(data)
            flag = 0
            for i in range(len(cars)):
                car = cars[i]
                if car.alive is True:
                    flag = 1
            if flag == 0:
                break
        bestAI = pick_best_AI(cars, carAis)
        cars.clear(); carAis.clear()
        for i in range(carnum):
            cars.append(myItem.car(2, 560, (15, 15)))
        carAis = controlAI.GeneOptimize.gene_algo(bestAI, carnum)



def cardecesion(v, dir, car):
    if v == -1:
        car.speed_down()
    elif v == 1:
        car.speed_up()
    if dir == -1:
        car.left_move()
    elif dir == 1:
        car.right_move()


def cardecesion_RL(v, dir, car):
    if v == 0:
        car.speed_down()
    elif v == 1:
        car.speed_up()
    if dir == 0:
        car.left_move()
    elif dir == 1:
        car.right_move()

if __name__ == '__main__':

    gameStart()

    # gameStart_by_RL()


