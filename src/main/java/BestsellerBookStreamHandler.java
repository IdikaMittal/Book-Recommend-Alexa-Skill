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
                        new BestSellerBookHandlers.AskBudgetIntentHandler(),
                        new BestSellerBookHandlers.NoIntentHandler(),
                        new BestSellerBookHandlers.YesIntentHandler(),
                        new BestSellerBookHandlers.AskedAnotherBookInfoIntentHandler(),
                        new BestSellerBookHandlers.CancelandStopIntentHandler(),
                        new BestSellerBookHandlers.BudgetIntentHandler(),
                        new BestSellerBookHandlers.CategoryIntentHandler(),
                        new BestSellerBookHandlers.AuthorIntentHandler(),
                        new BestSellerBookHandlers.ContinueIntentHandler(),
                        new BestSellerBookHandlers.MoreIntentHandler(),
                        new BestSellerBookHandlers.FallbackIntentHandler(),
                        new BestSellerBookHandlers.BookDescriptionIntentHandler(),
                        new BestSellerBookHandlers.GiveBookInfoIntentHandler())
                .build();
    }
    public BestsellerBookStreamHandler() {
        super(getSkill());
    }
}