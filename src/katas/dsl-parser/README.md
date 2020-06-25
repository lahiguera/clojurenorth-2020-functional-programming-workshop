# DSL for parsing bytearrays

## Intro
Suppose you have a bytearray holding a:
 -----------------------------------------------------------------------------------
 |  32-bit int | 16 empty bits | string 10 bytes | double precision floating value |
 -----------------------------------------------------------------------------------

```clojure
[[:read-int-32 :big-endian? true]
 [:skip-bytes 2]
 [:read-string 10 :encoding :UTF-8]
 [:read-float-64 :big-endian? true]] 

==>
parsing function
==>
[10 "0123456789" 43.43]
```