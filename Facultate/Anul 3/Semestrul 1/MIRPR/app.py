from pathlib import Path
import tkinter as tk
from tkinter import filedialog, messagebox
from tkinter.scrolledtext import ScrolledText

EXAMPLE = (
    "Un client foloseste un magazin online cu o interfata React, "
    "un API Python, o baza de date PostgreSQL si un procesator extern de plati. "
    "API-ul contine un controller si un serviciu pentru comenzi."
)


def predict(description):
    return Path("examples/workspace.dsl").read_text(encoding="utf-8")

class Application(tk.Tk):
    def __init__(self):

        super().__init__()

        self.result = ""
        self.input = ScrolledText(self, height=5, wrap="word")
        self.input.pack(fill="x", padx=10, pady=5)
        self.input.insert("1.0", EXAMPLE)

        buttons = tk.Frame(self)
        buttons.pack(padx=10, pady=5)
        tk.Button(buttons, text="Genereaza", command=self.generate).pack(side="left")

        self.save_button = tk.Button(buttons, text="Salveaza DSL", command=self.save, state="disabled")
        self.save_button.pack(side="left", padx=5)

        self.output = ScrolledText(self, wrap="word", state="disabled")
        self.output.pack(fill="both", expand=True, padx=10, pady=(5, 10))

    def generate(self):
        description = self.input.get("1.0")
        try:
            result = predict(description)
        except OSError as error:
            messagebox.showerror("Eroare", str(error))
            return
        self.result = result
        self.output.config(state="normal")
        self.output.delete("1.0", "end")
        self.output.insert("1.0", result)
        self.output.config(state="disabled")
        self.save_button.config(state="normal")

    def save(self):
        filename = filedialog.asksaveasfilename(
            initialfile="workspace.dsl", defaultextension=".dsl",
            filetypes=[("Structurizr DSL", "*.dsl")],
        )
        if filename:
            try:
                Path(filename).write_text(self.result, encoding="utf-8")
            except OSError as error:
                messagebox.showerror("Eroare", str(error))


if __name__ == "__main__":
    Application().mainloop()
