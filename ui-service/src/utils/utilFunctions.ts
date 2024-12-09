
export const isValidParentheses = (str: string) => {
  const stack: string[] = [];
  const pairs: Map<string, string> = new Map([["(", ")"], ["[","]"], ["{","}"]]);
  for (const char of str) {
    if (pairs.get(char)) {
      stack.push(char);
    } else if (
      char === ")" ||
          char === "]" ||
          char === "}"
    ) {
      if (
        pairs.get(stack.pop() || "") !==
              char
      ) {
        return false;
      }
    }
  }

  return stack.length === 0;
};