class Solution:
    def finalValueAfterOperations(self, operations: List[str]) -> int:
        def findOperator(ops):
            if "-" == ops[0] or "-" == ops[-1]:
                return "-"
            else:
                return "+"
        res = 0
        for op in operations:
            operator = findOperator(op)
            if operator == "-":
                res -= 1
            else:
                res += 1
        return res

