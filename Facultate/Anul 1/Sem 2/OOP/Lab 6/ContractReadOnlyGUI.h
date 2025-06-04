#pragma once
#include <qwidget.h>
#include <qpainter.h>
#include <qcolor.h>
#include <QPaintEvent>
#include <ctime>
#include <cstdlib>
#include "service.h"

class ContractReadOnlyGUI final : public QWidget, public Observer {
public:
    explicit ContractReadOnlyGUI(Service& service,QWidget* parent=nullptr) : service{ service },QWidget(parent) {
        initConnect();
        loadData();
        std::srand(static_cast<unsigned int>(std::time(nullptr)));
        startTimer(1000);
        resize(800, 600);
    }

    void paintEvent(QPaintEvent* ev) override {
        QPainter element{ this };
        for (int i = 0; i < nrDisciplineContract; i++) {
            const int numarRandom = std::rand() % 6;
            const int numarRandomX = std::rand() % (width() - 50);
            const int numarRandomY = std::rand() % (height() - 50);
            const int elementWidth = std::rand() % 50 + 10;
            const int elementHeight = std::rand() % 50 + 10;
            QColor culoare = culoareRandom();
            element.setPen(culoare);
            element.setBrush(culoare);
            switch (numarRandom) {
                case 0:
                    element.drawLine(numarRandomX, numarRandomY, elementWidth, elementHeight);
                break;
                case 1:
                    element.drawRect(numarRandomX, numarRandomY, elementWidth, elementHeight);
                break;
                case 2:
                    element.drawArc(numarRandomX, numarRandomY, elementWidth, elementHeight, 0, 5760);
                break;
                case 3:
                    element.drawEllipse(numarRandomX, numarRandomY, elementWidth / 2, elementHeight / 2);
                break;
                case 4:
                    element.drawPoint(numarRandomX, numarRandomY);
                break;
                case 5:
                    element.drawPie(numarRandomX, numarRandomY, elementWidth, elementHeight, 0, 5760);
                break;
                default: ;
            }
        }
    }

    void update() override {
        loadData();
        repaint();
    }

private:
    Service& service;
    int nrDisciplineContract{};

    void loadData() {
        nrDisciplineContract = service.getContractSize();
    }

    void initConnect() {
        service.addObserver(this);
    }

    static QColor culoareRandom() {
        const int rosu = std::rand() % 256;
        const int verde = std::rand() % 256;
        const int albastru = std::rand() % 256;
        return {rosu, verde, albastru};
    }

    ~ContractReadOnlyGUI() override {
        service.removeObserver(this);
    }
};