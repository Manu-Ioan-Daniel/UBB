from PIL import Image
from pathlib import Path
from matplotlib import pyplot as plt

#2.1

plt.figure()

im = Image.open("images/BERT.png")

plt.imshow(im)
plt.show()

#2.2

folder = Path("images")
images = []
for file in folder.iterdir():
    try:
        im = Image.open(file)
        if im.size != (128, 128):
            im = im.resize((128,128))
        images.append(im)
    except Exception as _:
        print(f"Nu am putut deschide {file}")


plt.figure()

cols = 3
rows = (len(images) + cols - 1) // cols

for i, img in enumerate(images):
    plt.subplot(rows, cols, i+1)
    plt.imshow(img)
    plt.axis("off")

plt.show()

#2.3

plt.figure()

for img in images:
    img2 = img.convert("L")
    plt.subplot(rows,cols, images.index(img)+1)
    plt.imshow(img2, cmap="gray")
    plt.axis("off")

plt.show()

#2.4

from PIL import ImageFilter

img = Image.open("images/BERT.png")
img_blur = img.filter(ImageFilter.GaussianBlur(radius=5))

plt.figure()

for i in range(2):
    plt.subplot(1,2,i+1)
    plt.imshow(img_blur if i == 1 else img)
    plt.axis("off")

plt.show()

#2.5

img = Image.open("images/chatGPT.png").convert("L")
edges = img.filter(ImageFilter.FIND_EDGES)
plt.figure()

for i in range(2):
    plt.subplot(1,2,i+1)
    plt.imshow(edges if i == 1 else img, cmap="gray")
    plt.axis("off")

plt.show()


















