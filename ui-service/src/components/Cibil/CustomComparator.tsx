import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";

export type Order = "asc" | "desc";

const DATE_FORMAT = "DD-MM-YYYY";

export function descendingComparator<T>(a: T, b: T, orderBy: keyof T): number {
  const valueA = a[orderBy];
  const valueB = b[orderBy];

  if (orderBy==="sanctionedDate" || orderBy==="reportedDate"){
    dayjs.extend(customParseFormat);
    if (dayjs(valueA as string, DATE_FORMAT).isBefore(dayjs(valueB as string, DATE_FORMAT), "date")){
      return 1;
    }
    if (dayjs(valueA as string, DATE_FORMAT).isAfter(dayjs(valueB as string, DATE_FORMAT), "date")){
      return -1;
    }
    return 0;
  } else {
    if (valueB < valueA) {
      return -1;
    }
    if (valueB > valueA) {
      return 1;
    }
    return 0;
  }
}

export function getComparator<T, Key extends keyof T>(order: Order, orderBy: Key): (a: T, b: T) => number {
  return order === "desc"
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

export function stableSort<T>(array: readonly T[], comparator: (a: T, b: T) => number): T[] {
  const stabilizedThis = array.map((el, index) => [el, index] as [T, number]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) {
      return order;
    }
    return a[1] - b[1];
  });
  return stabilizedThis.map((el) => el[0]);
}