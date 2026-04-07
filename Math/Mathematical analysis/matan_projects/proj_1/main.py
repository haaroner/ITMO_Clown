import math

import matplotlib.pyplot as plt
import numpy as np

def f(x):
    #return math.sin(x)
    return math.pow(math.e, -x)

def draw_squares(squares, number):
    S = []
    for square in squares:
        S.append(square[1]*square[2])
        plt.bar(x=square[0], height=square[1], width=square[2], alpha=1)
    print("Resulting sum = " + str(sum(S)))

def draw_trapezoids(trapezoids, number):
    S = []
    for trap in trapezoids:
        data = list(map(abs, trap[2]))
        a = trap[0][1] - trap[0][0]
        L_b = a * max(data)/(max(data) - min(data))
        S_b = 0.5 * max(data) * L_b
        S_m = 0.5 * min(data) * (L_b-a)
        S.append(S_b - S_m)
        plt.fill_between(trap[0], trap[1], trap[2], alpha=0.3)
    print("Resulting sum = " + str(sum(S)))

if __name__ == "__main__":
    while True:
        step = float(input("step "))
        e_step = 0.0001
        b = 1
        a = 0
        length = b - a
        number = length/step
        print(number)
        X_i = [x*e_step for x in range(0, int(length / e_step)+1)]
        X_e = [x*step for x in range(0, int(length / step)+1)]
        Y = [f(x) for x in X_i]
        mode = ''
        while mode not in ["left", "right", "middle", "trap", "end"]:
            mode = input('mode ')
        plt.clf()
        if mode == "end":
            plt.close()
            exit(0)
        plt.plot(X_i, Y, label='e^-x', color='r')
        if mode == "left":
            X = X_e[:-1]
            left_squares = [[x + step / 2, f(x), step] for x in X]
            draw_squares(left_squares, number)
        elif mode == "right":
            X = X_e[1:]
            right_squares = [[x - step / 2,f(x), step] for x in X]
            draw_squares(right_squares,number)
        elif mode == "middle":
            X = X_e[1::]
            middle_squares = [[x-step/2, f(x-step/2), step] for x in X]
            draw_squares(middle_squares,number)
        elif mode == "trap":
            X = X_e
            trapezoids = [[[X[i], X[i+1]], [0, 0], [f(X[i]), f(X[i+1])], step] for i in range(len(X)-1)]
            draw_trapezoids(trapezoids, number)
        plt.show()