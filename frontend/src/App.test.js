import { render, screen } from "@testing-library/react";
import App from "./App";

test("renders header", () => {
  render(<App />);
  const linkElement = screen.getByText("POTATO DAYS");
  expect(linkElement).toBeInTheDocument();
});
