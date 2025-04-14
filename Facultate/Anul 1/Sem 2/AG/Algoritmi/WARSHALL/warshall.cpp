void FloydWarshall(vector<vector<int>>& graph) {
    int V = graph.size();

    // Floyd-Warshall main loop
    for (int k = 0; k < V; k++) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (graph[i][k] != INF && graph[k][j] != INF) {
                    graph[i][j] = min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }
    }
}
