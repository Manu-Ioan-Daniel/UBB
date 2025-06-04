#pragma once
#include <QAbstractTableModel>
#include <vector>

#include "disciplina.h"

class ContractTableModel : public QAbstractTableModel {
    Q_OBJECT

private:
    std::vector<Disciplina> discipline;

public:
    explicit ContractTableModel(QObject* parent = nullptr)
        : QAbstractTableModel(parent) {}

    [[nodiscard]] int rowCount(const QModelIndex& parent = QModelIndex()) const override {
        Q_UNUSED(parent);
        return static_cast<int>(discipline.size());
    }

    [[nodiscard]] int columnCount(const QModelIndex& parent = QModelIndex()) const override {
        Q_UNUSED(parent);
        return 4; // Denumire, Nr Ore, Tip, Cadru Didactic
    }

    [[nodiscard]] QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
        if (!index.isValid() || role != Qt::DisplayRole)
            return {};

        const auto& d = discipline.at(index.row());
        switch (index.column()) {
            case 0: return QString::fromStdString(d.getDenumire());
            case 1: return d.getNrOre();
            case 2: return QString::fromStdString(d.getTip());
            case 3: return QString::fromStdString(d.getCadruDidactic());
            default: return {};
        }
    }

    [[nodiscard]] QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override {
        if (role != Qt::DisplayRole || orientation != Qt::Horizontal)
            return {};

        switch (section) {
            case 0: return "Disciplina";
            case 1: return "Nr Ore";
            case 2: return "Tip";
            case 3: return "Cadru Didactic";
            default: return {};
        }
    }

    void setDiscipline(const std::vector<Disciplina>& newDisc) {
        beginResetModel();
        discipline = newDisc;
        endResetModel();
    }

    [[nodiscard]] const Disciplina& getDisciplinaAt(const int row) const {
        return discipline.at(row);
    }
};
