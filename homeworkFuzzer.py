import random
import binascii


def chooseOperator():
    return random.choice(["trim", "bit flip", "swap"])


def swap(line):
    newLine = ""
    length = len(line)
    pos = random.choice([i for i in range(length-1)])
    char1 = line[pos]
    char2 = line[pos+1]

    for i in range(pos):
        newLine += line[i]
    newLine += char2
    newLine += char1

    for i in range(pos+2, length):
        newLine += line[i]

    return newLine


def stringToBits(line):
    byte_array = bytearray(line, "utf8")
    byte_list = []
    byte_list2 = []
    for byte in byte_array:
        binary_representation = bin(byte)
        byte_list.append(binary_representation)

    for i in byte_list:
        newStr = ""
        
        for j in range(2, 5):
            newStr += i[j]
        
        i = newStr
        byte_list2.append(i)

    return byte_list2

def bitsToString(byte_list):
    words = ""
    for l in byte_list:
        binary_values = l.split()
        ascii_string = ""
        for binary_value in binary_values:
            an_integer = int(binary_value, 2)
            ascii_character = chr(an_integer)
            ascii_string += ascii_character

        words += ascii_string

    return words


def flip(line):
    newLine2 = ""
    byte_list = stringToBits(line)
    listLength = len(byte_list)
    randomNumber = random.choice([i for i in range(listLength-1)])
    letter = byte_list[randomNumber]

    length = len(letter)
    pos = random.choice([i for i in range(length-1)])

    if letter[pos] == 0:
        for i in range(pos):
            newLine2 += line[i]
        newLine2 += "1"

        for i in range(pos+1, length):
            newLine2 += line[i]

    elif letter[pos] == 0:
        for i in range(pos):
            newLine2 += line[i]
        newLine2 += "0"

        for i in range(pos+1, length):
            newLine2 += line[i]

    letter = newLine2
    newString = ""
    for i in byte_list:
        newString += i
        newString += " "
    return bitsToString(newString)


def trim(line):
    sentence = ""
    count = random.choice([i for i in range(len(line)-1)])
    for j in range (count-1):
        sentence += line[j]

    sentence += "\n"
    return sentence


def homeworkFuzz(file_name):
    array1 = []
    with open(file_name) as f:
        array1 = f.readlines()

    for i in range(len(array1)):
        op = chooseOperator()
        if op == "swap":
            array1[i] = swap(array1[i])

        elif op == "bit flip":
            array1[i] = flip(array1[i])

        else:
            array1[i] = trim(array1[i])

    with open('homeworkOutput.txt', 'w') as filehandle:
        for listitem in array1:
            filehandle.write('%s' % listitem)

    return array1


print(homeworkFuzz("homeworkText.txt"))
