#include <pthread.h>

pthread_barrier_t b1, b2;

void* f1(void* a) {
    pthread_barrier_wait(&b1);
    return NULL;
}

void* f2(void* a) {
    pthread_barrier_wait(&b2);
    return NULL;
}

int main() {
    int i;
    const int T=6;
    const int N2=2;
    const int N1=3;
    pthread_t t[T][2];

    pthread_barrier_init(&b1, NULL, N1);
    pthread_barrier_init(&b2, NULL, N2);

    for(i = 0; i < T; i++) {
        pthread_create(&t[i][0], NULL, f1, NULL);
        pthread_create(&t[i][1], NULL, f2, NULL);
    }

    for(i = 0; i < T; i++) {
        pthread_join(t[i][0], NULL);
        pthread_join(t[i][1], NULL);
    }

    pthread_barrier_destroy(&b1);
    pthread_barrier_destroy(&b2);
    return 0;
}