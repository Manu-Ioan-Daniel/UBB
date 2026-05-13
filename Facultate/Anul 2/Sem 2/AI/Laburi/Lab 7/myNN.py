import math
import random

def dot(A, B):
    rows_A, cols_A = len(A), len(A[0])
    cols_B = len(B[0])
    return [
        [sum(A[i][k] * B[k][j] for k in range(cols_A)) for j in range(cols_B)]
        for i in range(rows_A)
    ]


def add_bias(Z, b):
    return [[Z[i][j] + b[0][j] for j in range(len(Z[0]))] for i in range(len(Z))]


def transpose(M):
    return [[M[i][j] for i in range(len(M))] for j in range(len(M[0]))]


def zeros(rows, cols):
    return [[0.0] * cols for _ in range(rows)]


def randn(rows, cols, scale=0.01):
    return [[random.gauss(0, 1) * scale for _ in range(cols)] for _ in range(rows)]


class NN:
    def __init__(self, n_features, n_classes, n_hidden):
        self.d = n_features
        self.n = n_classes
        self.h = n_hidden

        self.W1 = randn(self.d, self.h)
        self.b1 = zeros(1, self.h)
        self.W2 = randn(self.h, self.n)
        self.b2 = zeros(1, self.n)

    def frwd_prop(self, x):
        Z1 = add_bias(dot(x, self.W1), self.b1)
        A1 = [[max(0.0, Z1[i][j]) for j in range(len(Z1[0]))] for i in range(len(Z1))]

        Z2 = add_bias(dot(A1, self.W2), self.b2)
        A2 = []
        for row in Z2:
            max_val = max(row)
            exps = [math.exp(v - max_val) for v in row]
            total = sum(exps)
            A2.append([e / total for e in exps])

        return A1, A2

    def ce_loss(self, y_true, y_pred_proba):
        n = len(y_true)
        return sum(-math.log(y_pred_proba[i][y_true[i]] + 1e-8) for i in range(n)) / n

    def backward_prop(self, x, y, A1, A2):
        n = len(y)
        cols_A2 = len(A2[0])
        cols_A1 = len(A1[0])

        dZ2 = [[A2[i][j] / n for j in range(cols_A2)] for i in range(n)]
        for i in range(n):
            dZ2[i][y[i]] -= 1.0 / n

        dW2 = dot(transpose(A1), dZ2)
        db2 = [[sum(dZ2[i][j] for i in range(n)) for j in range(cols_A2)]]

        dA1 = dot(dZ2, transpose(self.W2))
        dZ1 = [
            [dA1[i][j] if A1[i][j] > 0 else 0.0 for j in range(cols_A1)]
            for i in range(n)
        ]

        dW1 = dot(transpose(x), dZ1)
        db1 = [[sum(dZ1[i][j] for i in range(n)) for j in range(cols_A1)]]

        return dW1, db1, dW2, db2

    def fit(self, x, y, reg=1e-3, max_iters=1000, eta=0.1):
        for i in range(max_iters):
            A1, A2 = self.frwd_prop(x)

            if i % 10 == 0:
                loss = self.ce_loss(y, A2)
                reg_loss = 0.5 * reg * (
                    sum(self.W1[r][c] ** 2 for r in range(len(self.W1)) for c in range(len(self.W1[0])))
                    + sum(self.W2[r][c] ** 2 for r in range(len(self.W2)) for c in range(len(self.W2[0])))
                )
                print(f"Iteratia {i}: loss {loss + reg_loss:.4f}")

            dW1, db1, dW2, db2 = self.backward_prop(x, y, A1, A2)

            for r in range(len(dW2)):
                for c in range(len(dW2[0])):
                    dW2[r][c] += reg * self.W2[r][c]
            for r in range(len(dW1)):
                for c in range(len(dW1[0])):
                    dW1[r][c] += reg * self.W1[r][c]

            for r in range(len(self.W1)):
                for c in range(len(self.W1[0])):
                    self.W1[r][c] -= eta * dW1[r][c]
            for r in range(len(self.W2)):
                for c in range(len(self.W2[0])):
                    self.W2[r][c] -= eta * dW2[r][c]
            for c in range(len(self.b1[0])):
                self.b1[0][c] -= eta * db1[0][c]
            for c in range(len(self.b2[0])):
                self.b2[0][c] -= eta * db2[0][c]

    def predict(self, x):
        _, y_pred = self.frwd_prop(x)
        return [max(range(len(row)), key=lambda j: row[j]) for row in y_pred]