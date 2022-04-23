package app.tool;

import app.exception.post.InvalidDateFormatEx;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ConverterTool {

  public LocalDate stringToLocalDate(String date) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      return LocalDate.parse(date, formatter);
    } catch (Exception e) {
      throw new InvalidDateFormatEx();
    }
  }
}
