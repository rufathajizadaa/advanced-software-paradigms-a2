def main():
    tpl = (1, 2, 3)
    lst = [1, 2, 3]

    print("tuple:", tpl.__sizeof__())
    print("list:", lst.__sizeof__())


if __name__ == "__main__":
    main()
