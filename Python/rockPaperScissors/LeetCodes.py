from typing import List


class Solution:

    def twoSum(self, nums: List[int], target: int) -> List[int]:
        vistited = {}
        for i in range(len(nums)):
            exitValue = target - nums[i]
            if exitValue in vistited:
                return [vistited.get(exitValue), i]
            vistited[nums[i]] = i

    def reverse(self, x: int) -> int:
        negative = False
        if x < 0:
            x = x * -1
            negative = True
        base = 10 ** (len(str(x))-1)
        rValue = 0
        while x != 0:
            lastDigit = int(x % 10)
            x = int(x/10)
            rValue += lastDigit*base
            base = int(base/10)
        if rValue > (2**31-1):
            return 0
        if negative:
            rValue = rValue * -1
        return rValue

    def isPalindrome(self, x: int) -> bool:
        if x < 0:
            return False
        if x < 10:
            return True
        digits = [int(n) for n in str(x)]
        fPointer = 0
        bPointer = len(digits) - 1
        while fPointer < bPointer:
            if digits[fPointer] != digits[bPointer]:
                return False
            fPointer += 1
            bPointer -= 1
        return True

    def romanToInt(self, s: str) -> int:
        numeralDict = {'I': 1, 'V': 5, 'X': 10, 'L': 50, 'C': 100, 'D': 500, 'M': 1000}
        if len(s) == 1:
            return numeralDict[s]
        numbers = [numeralDict[l] for l in s]
        truncatedNumbers = []
        i = 0
        length = len(numbers)
        while i < length:
            current = numbers[i]
            if (i+1) != length and current < numbers[i+1]:
                truncatedNumbers.append(numbers[i+1] - current)
                i += 1
            else:
                truncatedNumbers.append(current)
            i += 1
        return sum(truncatedNumbers)

    def isValid(self, s: str) -> bool:
        if len(s) == 0:
            return True
        braces = {')': '(', '}': '{', ']': '['}
        expected = []
        for char in s:
            if char in braces.values():
                expected.append(char)
            elif char in braces.keys():
                if len(expected) == 0:
                    return False
                if braces.get(char) == expected[-1]:
                    expected.pop()
                else:
                    return False
        return len(expected) == 0

    def searchInsert(self, nums: List[int], target: int) -> int:
        for i in range(len(nums)):
            if nums[i] >= target:
                return i
        return len(nums)

    def maxSubArray(self, nums: List[int]) -> int:
        cur = maxSum = nums[0]
        for i in range(1, len(nums)):
            cur = max(nums[i], cur + nums[i])
            maxSum = max(cur, maxSum)
        return maxSum

    def lengthOfLastWord(self, s: str) -> int:
        currSize = 0
        for i in reversed(range(len(s))):
            if s[i] != ' ':
                currSize += 1
            if s[i] == ' ' and currSize > 0:
                return currSize
        return currSize

    def intToRoman(self, num: int) -> str:
        numeralDict = {1: 'I', 4: 'IV', 5: 'V', 9: 'IX', 10: 'X', 40: 'XL',
                       50: 'L', 90: 'XC', 100: 'C', 400: 'CD', 500: 'D', 900: 'CM',
                       1000: 'M'}
        numerals = []
        currentNum = num
        while currentNum > 0:
            nextRemovalNum = max(list(filter(lambda x: x <= currentNum, numeralDict.keys())))
            currentNum -= nextRemovalNum
            numerals.append(numeralDict.get(nextRemovalNum))
        return ''.join(numerals)

    def maxArea(self, height: List[int]) -> int:
        maxVal = 0
        l = 0
        r = len(height)-1
        while l < r:
            maxVal = max(maxVal, (abs(r-l)*min(height[l], height[r])))
            if height[l] > height[r]:
                r -= 1
            else:
                l += 1
        return maxVal
