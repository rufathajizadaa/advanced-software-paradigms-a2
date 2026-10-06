from pathlib import Path

import numpy as np
from PIL import Image, ImageDraw


TASK_DIR = Path(__file__).resolve().parents[2]
OUTPUT_DIR = TASK_DIR / "output"
NUMPY_FILE = OUTPUT_DIR / "numpy_slice.csv"
JAVA_FILE = OUTPUT_DIR / "java_slice.csv"
IMAGE_FILE = OUTPUT_DIR / "slice_comparison.png"

CELL_SIZE = 80
TOP_SPACE = 70
TITLE_SPACE = 35
GAP = 50
MARGIN = 30


def read_matrix(path):
    data = np.loadtxt(path, delimiter=",")
    return np.atleast_2d(data)


def value_color(value, minimum, maximum):
    if maximum == minimum:
        ratio = 0.5
    else:
        ratio = (value - minimum) / (maximum - minimum)

    red = int(60 + 140 * ratio)
    green = int(120 + 80 * (1 - ratio))
    blue = int(210 - 120 * ratio)
    return red, green, blue


def draw_matrix(draw, matrix, x_start, y_start, title, minimum, maximum):
    draw.text((x_start, y_start - TITLE_SPACE), title, fill="black")

    for i in range(matrix.shape[0]):
        for j in range(matrix.shape[1]):
            x1 = x_start + j * CELL_SIZE
            y1 = y_start + i * CELL_SIZE
            x2 = x1 + CELL_SIZE
            y2 = y1 + CELL_SIZE

            color = value_color(matrix[i, j], minimum, maximum)
            draw.rectangle((x1, y1, x2, y2), fill=color, outline="black")

            text = f"{matrix[i, j]:g}"
            text_box = draw.textbbox((0, 0), text)
            text_width = text_box[2] - text_box[0]
            text_height = text_box[3] - text_box[1]
            text_x = x1 + (CELL_SIZE - text_width) / 2
            text_y = y1 + (CELL_SIZE - text_height) / 2
            draw.text((text_x, text_y), text, fill="white")


def main():
    if not NUMPY_FILE.exists() or not JAVA_FILE.exists():
        print("Run both slicing programs before creating the comparison image.")
        print(f"Missing files are expected at {NUMPY_FILE} and {JAVA_FILE}.")
        return

    numpy_result = read_matrix(NUMPY_FILE)
    java_result = read_matrix(JAVA_FILE)
    same_result = numpy_result.shape == java_result.shape and np.allclose(numpy_result, java_result)

    max_rows = max(numpy_result.shape[0], java_result.shape[0])
    numpy_width = numpy_result.shape[1] * CELL_SIZE
    java_width = java_result.shape[1] * CELL_SIZE
    width = MARGIN * 2 + numpy_width + GAP + java_width
    height = TOP_SPACE + TITLE_SPACE + max_rows * CELL_SIZE + MARGIN

    image = Image.new("RGB", (width, height), "white")
    draw = ImageDraw.Draw(image)

    status = "Same result" if same_result else "Different result"
    draw.text((MARGIN, MARGIN), status, fill="black")

    combined = np.concatenate((numpy_result.flatten(), java_result.flatten()))
    minimum = float(np.min(combined))
    maximum = float(np.max(combined))

    y_start = TOP_SPACE + TITLE_SPACE
    numpy_x = MARGIN
    java_x = MARGIN + numpy_width + GAP

    draw_matrix(draw, numpy_result, numpy_x, y_start, "NumPy result", minimum, maximum)
    draw_matrix(draw, java_result, java_x, y_start, "Java result", minimum, maximum)

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    image.save(IMAGE_FILE)

    print(f"Saved comparison image to {IMAGE_FILE}")


if __name__ == "__main__":
    main()
