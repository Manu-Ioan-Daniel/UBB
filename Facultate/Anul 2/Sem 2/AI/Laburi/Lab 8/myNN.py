import numpy as np


class NN:
    def __init__(self, n_features, n_classes, n_hidden):
        self.d = n_features
        self.n = n_classes
        self.h = n_hidden

        self.W1 = 0.01 * np.random.randn(self.d, self.h)
        self.b1 = np.zeros((1, self.h))
        self.W2 = 0.01 * np.random.randn(self.h, self.n)
        self.b2 = np.zeros((1, self.n))

    def frwd_prop(self, x):
        z1 = np.dot(x, self.W1) + self.b1
        A1 = np.maximum(0, z1)
        z2 = np.dot(A1, self.W2) + self.b2
        exp_scores = np.exp(z2 - np.max(z2, axis=1, keepdims=True))
        A2 = exp_scores / np.sum(exp_scores, axis=1, keepdims=True)
        return A1, A2

    def ce_loss(self, y_true, y_pred_proba):
        num_examples = y_true.shape[0]
        log_probs = -np.log(y_pred_proba[range(num_examples), y_true] + 1e-8)
        return np.sum(log_probs) / num_examples

    def backward_prop(self, x, y, A1, A2):
        num_examples = y.shape[0]
        dZ2 = A2.copy()
        dZ2[range(num_examples), y] -= 1
        dZ2 /= num_examples

        dW2 = np.dot(A1.T, dZ2)
        db2 = np.sum(dZ2, axis=0, keepdims=True)

        dA1 = np.dot(dZ2, self.W2.T)
        dZ1 = dA1.copy()
        dZ1[A1 <= 0] = 0

        dW1 = np.dot(x.T, dZ1)
        db1 = np.sum(dZ1, axis=0, keepdims=True)

        return dW1, db1, dW2, db2

    def fit(self, x, y, reg=1e-3, max_iters=1000, eta=0.1):
        for i in range(max_iters):
            A1, A2 = self.frwd_prop(x)

            if i % 100 == 0:
                loss = self.ce_loss(y, A2)
                reg_loss = 0.5 * reg * (np.sum(self.W1 ** 2) + np.sum(self.W2 ** 2))
                print(f"Iteratia {i}: loss {loss + reg_loss:.4f}")

            dW1, db1, dW2, db2 = self.backward_prop(x, y, A1, A2)

            dW2 += reg * self.W2
            dW1 += reg * self.W1

            self.W1 -= eta * dW1
            self.W2 -= eta * dW2
            self.b1 -= eta * db1
            self.b2 -= eta * db2

    def predict(self, x):
        _, y_pred = self.frwd_prop(x)
        return np.argmax(y_pred, axis=1)