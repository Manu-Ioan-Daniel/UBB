#include <iostream>
#include <queue>
#include <unordered_map>
#include <vector>
#include <string>

using namespace std;

// Structura nod Huffman
struct Node {
    char ch;
    int freq;
    Node *left, *right;

    Node(const char c, const int f) : ch(c), freq(f), left(nullptr), right(nullptr) {}
    Node(const int f, Node* l, Node* r) : ch('\0'), freq(f), left(l), right(r) {}
};

// Comparator pentru priority_queue (min-heap)
struct Compare {
    bool operator()(const Node* a, const Node* b) const {
        return a->freq > b->freq;  // min-heap
    }
};

// Generează coduri Huffman prin DFS
void buildCodes(const Node* root, const string& code, unordered_map<char, string>& huffmanCode) {
    if (!root) return;

    if (!root->left && !root->right) {
        huffmanCode[root->ch] = code;
    }

    buildCodes(root->left, code + "0", huffmanCode);
    buildCodes(root->right, code + "1", huffmanCode);
}

// Codifică un text
string encode(const string& text, unordered_map<char, string>& huffmanCode) {
    string encoded;
    for (char c : text) {
        encoded += huffmanCode[c];
    }
    return encoded;
}

// Decodifică un text Huffman
string decode(Node* root, const string& encoded) {
    string decoded;
    Node* current = root;
    for (char bit : encoded) {
        if (bit == '0') current = current->left;
        else current = current->right;

        if (!current->left && !current->right) {
            decoded += current->ch;
            current = root;
        }
    }
    return decoded;
}

// HUFFMAN(C): Construiește arborele Huffman
Node* buildHuffmanTree(const string& text) {
    unordered_map<char, int> freq;
    for (char c : text) freq[c]++;

    priority_queue<Node*, vector<Node*>, Compare> pq;

    for (auto [ch, f] : freq) {
        pq.push(new Node(ch, f));
    }

    while (pq.size() > 1) {
        Node* x = pq.top(); pq.pop();
        Node* y = pq.top(); pq.pop();
        auto z = new Node(x->freq + y->freq, x, y);
        pq.push(z);
    }

    return pq.top(); // rădăcina
}

int main() {
    const string text = "edisugepula";

    // 1. Construiește arborele Huffman
    Node* root = buildHuffmanTree(text);

    // 2. Generează codurile Huffman
    unordered_map<char, string> huffmanCode;
    buildCodes(root, "", huffmanCode);

    cout << "Coduri Huffman:\n";
    for (const auto& [ch, code] : huffmanCode) {
        cout << ch << ": " << code << "\n";
    }

    // 3. Codificare
    const string encoded = encode(text, huffmanCode);
    cout << "\nText codificat: " << encoded << "\n";

    // 4. Decodificare
    const string decoded = decode(root, encoded);
    cout << "Text decodificat: " << decoded << "\n";

    return 0;
}
