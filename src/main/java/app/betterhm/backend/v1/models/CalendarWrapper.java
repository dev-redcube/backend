package app.betterhm.backend.v1.models;

import java.util.List;

public class CalendarWrapper {
  private List<CalendarElement> data;

  public CalendarWrapper(List<CalendarElement> elements) {
    this.data = elements;
  }

  public List<CalendarElement> getData() {
    return data;
  }

  public void setData(List<CalendarElement> data) {
    this.data = data;
  }
}
