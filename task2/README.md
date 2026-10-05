# Task 2: Tuple and List Size in Python

## Purpose

This task compares the memory size that Python reports for a tuple and a list with the same three integer values:

```python
tpl = (1, 2, 3)
lst = [1, 2, 3]

print(tpl.__sizeof__())
print(lst.__sizeof__())
```

The goal is not only to print the numbers, but also to understand why the two objects use different amounts of memory.

## Experiment

First, I checked the Python version in the terminal:

```text
hacizadarufat@anonymous A2 % python3 --version
Python 3.9.6
```

Then I ran this code using that Python version:

```python
def main():
    tpl = (1, 2, 3)
    lst = [1, 2, 3]

    print("tuple:", tpl.__sizeof__())
    print("list:", lst.__sizeof__())


if __name__ == "__main__":
    main()
```

The output on my machine was:

```text
hacizadarufat@anonymous A2 % python3 task2/code/size_check.py
tuple: 48
list: 104
```

`__sizeof__()` reports the memory used by the object itself. It does not include extra garbage collector overhead.

## Explanation

Both `tpl` and `lst` refer to three integer objects, but the container does not store the integer data directly. In CPython, a tuple or list stores references to the objects inside it. Because of this, the measured size mainly shows the memory used by the container itself, not the full memory cost of the integer objects `1`, `2`, and `3`.

A tuple is immutable, so after `(1, 2, 3)` is created, the number of elements cannot change. Since CPython knows the tuple will not grow, it can allocate only the space needed for the tuple structure and its three references. This is why the tuple has the smaller reported size.

A list is mutable, so it must support changes such as `append()`, `extend()`, item assignment like `lst[0] = value`, and deletion with `del lst[0]`. To make growth efficient, CPython usually keeps some unused space inside the list. This means the list does not need to request a new memory block every time one element is added. The downside is that a list may use more memory than a tuple even when both show the same values.

In my run, the tuple reported 48 bytes and the list reported 104 bytes. The list is larger because its structure can change and because it keeps extra capacity for possible future growth.

## Conclusion

The result shows that flexibility has a memory cost in Python. A tuple can be stored in a smaller space because its size is fixed when it is created. A list needs more internal support because it can grow, shrink, and change while the program is running. In small programs, the difference between 48 and 104 bytes is usually not a big problem. Still, this example is useful because it shows a common programming language tradeoff: data structures that are easier to change often need extra memory to manage that flexibility.
