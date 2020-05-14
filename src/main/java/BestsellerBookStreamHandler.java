import BestSellerBookHandlers.*;
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;


public class BestsellerBookStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .withSkillId("amzn1.ask.skill.aca46535-088c-4aa1-aec7-24509a08facf")
                .addRequestHandlers(
                        new BestSellerBookHandlers.LaunchRequestHandler(),
                        new BestSellerBookHandlers.CategoryIntentHandler(),
                        new BestSellerBookHandlers.BestsellerIntentHandler(),
                        new BestSellerBookHandlers.NoIntentHandler(),
                        new BestSellerBookHandlers.YesIntentHandler(),
                        new BestSellerBookHandlers.SameIntentHandler(),
                        new BestSellerBookHandlers.AnotherIntentHandler())
                .build();
    }
    public BestsellerBookStreamHandler() {
        super(getSkill());
    }
}