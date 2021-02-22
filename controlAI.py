# coding: utf-8

import numpy as np
from copy import deepcopy

class Data:
    def __init__(self, x):
        self.x = x

    def forward(self):
        # Mini-batch
        return self.x


class FullyConnect:
    def __init__(self, l_x, l_y, weights=None, bias=None):
        self.l_x = l_x
        self.l_y = l_y
        self.weights = np.random.randn(l_y, l_x) * np.sqrt(2 / l_x)
        self.bias = np.random.randn(l_y, 1)

    def forward(self, x):
        self.y = np.dot(self.weights, x) + self.bias
        return self.y


# Sigmoid
class Sigmoid:
    def __init__(self):
        pass

    def sigmoid(self, x):
        return 1 / (1 + np.exp(-x))

    def forward(self, x):
        self.x = x
        self.y = self.sigmoid(x)
        return self.y


class carAI:
    def __init__(self, node_list=[7, 15, 2]):
        self.fullyconnect_list = []
        for i in range(len(node_list) - 1):
            linear = FullyConnect(node_list[i], node_list[i + 1])
            self.fullyconnect_list.append(linear)

    def _adjust(self, t, v1, v2):
        if t < v1:
            return -1
        elif t > v2:
            return 1

    def _car_v_adjust(self, v_flag, v_thre1=0.3, v_thre2=0.7):
        car_v = self._adjust(v_flag, v_thre1, v_thre2)
        return car_v

    def _car_dir_adjust(self, dir_flag, dir_thre1=0.2, dir_thre2=0.8):
        car_dir = self._adjust(dir_flag, dir_thre1, dir_thre2)
        return car_dir

    def forward(self, x):
        for linear in self.fullyconnect_list:
            x = linear.forward(x)
            x = Sigmoid().forward(x)


        dir_flag = x[0][0]
        v_flag = x[1][0]
        car_v = self._car_v_adjust(v_flag)
        car_dir = self._car_dir_adjust(dir_flag)

        return (car_v, car_dir)

    def assign_weights(self, weightsList=None, biasList=None):
        for i in range(len(self.fullyconnect_list)):
            linear = self.fullyconnect_list[i]
            linear.weights = deepcopy(weightsList[i])
            linear.bias = deepcopy(biasList[i])

    def get_weights(self):
        weightsList = []
        biasList = []

        for i in range(len(self.fullyconnect_list)):
            linear = self.fullyconnect_list[i]
            weightsList.append(linear.weights)
            biasList.append(linear.bias)

        return weightsList, biasList

class GeneOptimize:
    def __init__(self):
        pass

    @classmethod
    def _distur_param(self, weightsList, biasList, k, r=0.1):
        carAIs = []
        for i in range(k):
            carai = carAI()
            w = deepcopy(weightsList)
            b = deepcopy(biasList)
            for i in range(len(w)):
                n, m = w[i].shape
                w[i] += r * np.random.randn(n, m)
            for i in range(len(b)):
                n, m = b[i].shape
                b[i] += r * np.random.randn(n, m)

            carai.assign_weights(w, b)
            carAIs.append(carai)
        return carAIs

    @classmethod
    def _replace_one_layer_param(self, weightsList, biasList, k):
        carAIs = []
        for i in range(k):
            carai = carAI()
            carAIs.append(carai)
            w = deepcopy(weightsList)
            b = deepcopy(biasList)
            for i in range(len(w)):
                t = np.random.rand(1)[0]
                if t < 0.3:
                    n, m = w[i].shape
                    w[i] += np.random.randn(n, m)
            for i in range(len(b)):
                t = np.random.rand(1)[0]
                if t < 0.7:
                    n, m = b[i].shape
                    b[i] += np.random.randn(n, m)
            carai.assign_weights(w, b)
            carAIs.append(carai)
        return carAIs


    @classmethod
    def gene_algo(self, parcarAI, n):
        carAIs = []
        carAIs.append(parcarAI)
        k1 = n // 4;
        k2 = n - 3 * k1 - 1
        weightsList, biasList = parcarAI.get_weights()
        # 0.1
        carAIs.extend(self._distur_param(weightsList, biasList, k1, 0.1))
        # 0.2
        carAIs.extend(self._distur_param(weightsList, biasList, k1, 0.5))
        # 0.6
        carAIs.extend(self._distur_param(weightsList, biasList, k1, 0.8))
        carAIs.extend(self._replace_one_layer_param(weightsList, biasList, k2))
        print("from genealgorithm:  ", parcarAI.get_weights() == carAIs[0].get_weights())
        return carAIs





