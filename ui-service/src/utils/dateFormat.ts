import {format} from "date-fns";

export function getFormattedDate(date: { value: string | number | Date; }) {
  return format(new Date(date.value), "dd/MM/yyyy");
}

export function getMonthYear(params: Date | string): string {

  const dt = new Date(params)
    .toLocaleDateString("en-GB", {
      day: "numeric",
      month: "short",
      year: "numeric",
    }).split(" ");
  return `${dt[1]}-${dt[2]}`;
}