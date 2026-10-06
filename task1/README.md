# Task 1: Investigation of Endianness

## Introduction

Endianness means the order in which a computer stores the bytes of a value in memory. If the value is only one byte, there is no endian issue, because there is only one part to store it. The issue occurs when the value has more than one byte, for example integers, floating-point numbers, and memory addresses.

The two common byte orders are big endian and little endian. In big endian, the most significant byte is stored first, at the lowest memory address. In little endian, the least significant byte is stored first. The value itself is the same, but the way its bytes are placed in memory is different.

## Example

For example, consider this hexadecimal value:

```text
0x16062004
```

This value consists of four bytes:

```text
16 06 20 04
```

In big endian order, memory stores it like this:

```text
Address: 1000 1001 1002 1003
Byte:     16   06   20   04
```

In little endian order, memory stores it like this:

```text
Address: 1000 1001 1002 1003
Byte:     04   20   06   16
```

The actual number is still `0x16062004`, only the byte order in memory changes.

## Why Endianness Matters

Endianness matters when data is moved between systems or when a program works with raw bytes directly. File formats, network protocols, binary serialization, embedded systems, and low-level debugging all need a clear byte order. If one system writes a value as little endian and another system reads it as big endian, the result can be wrong.

Network communication is a common example. Protocols usually define one standard byte order so that machines with different internal architectures can exchange data correctly. Without this rule, the same packet could be understood differently by different computers.

Endianness is important in systems programming as well. When working with pointers, binary files, memory dumps, or hardware registers, a programmer may need to know the real byte layout. High-level languages often hide this detail, but it still exists at the lower level.

## Critique

Endianness is useful, but it can also be confusing. From a programmer's point of view, a value like `0x16062004` may look like it should have one clear memory representation. In reality, programmers have to remember extra rules when they work close to hardware or binary data.

Little endian can be useful for some low-level operations because the least significant byte comes first. Big endian feels more natural for humans when reading hexadecimal numbers because the bytes appear in the same order as the written value.

My criticism is that the idea of endianness is not very complex by itself. The hard part is that mistakes are not always visible at the beginning. A program may compile and run, but the data may still be read in the wrong order. This makes endian bugs difficult to find, especially when binary data is shared between different platforms.

The best approach is to define byte order clearly in file formats, protocols, and APIs. Programmers should also use standard conversion functions instead of manually changing the byte order, unless there is a strong reason to do it.

## Conclusion

Endianness shows how a small hardware-level decision can affect software design. Most application programmers do not think about byte order every day, but it becomes important in networking, binary files, embedded programming, and debugging. Big endian and little endian both work. The problem starts when data is written with one assumption and read with another. For this reason, clear standards are more important than saying one byte order is always better than the other.

