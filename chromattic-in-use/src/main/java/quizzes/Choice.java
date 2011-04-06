package quizzes;

import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

@PrimaryType(name = "qz:choice")
public abstract class Choice
{
   @Property(name = "description")
   public abstract String getDescription();
   
   public abstract void setDescription(String des);
}
