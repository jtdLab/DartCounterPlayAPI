package dartServer.commons.parsing.validators;

import dartServer.commons.packets.Packet;
import dartServer.commons.packets.PacketContainer;
import dartServer.commons.packets.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class JsonValidator {

    static final Logger logger = LoggerFactory.getLogger(JsonValidator.class);

    private JsonValidator() {
        throw new IllegalStateException("Can not be instantiated!");
    }

    /**
     * Validates a {@link PacketContainer} using the annotated attributes
     *
     * @param p The packet container to validate
     * @return True if the packet container passes validation, false otherwise
     */
    public static boolean isPacketContainerValid(PacketContainer p) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PacketContainer>> constraintViolations = validator.validate(p);

        if (constraintViolations.size() > 0) {
            logger.warn("Validation of packet " + p.getPayloadType() + " returned errors!");
            for (ConstraintViolation c : constraintViolations) {
                logger.debug("Validation error: " + c.getPropertyPath() + " " + c.getMessage());
            }
        }

        return constraintViolations.size() == 0;
    }

    /**
     * Validates a {@link Packet} using the annotated attributes
     *
     * @param p The packet to validate
     * @return True if the packet passes validation, false otherwise
     */
    public static boolean isPacketValid(Packet p) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Packet>> constraintViolations = validator.validate(p);

        if (constraintViolations.size() > 0) {
            logger.warn("Validation of packet " + PacketType.forClass(p.getClass()) + " returned errors!");
            for (ConstraintViolation c : constraintViolations) {
                logger.debug("Validation error: " + c.getPropertyPath() + " " + c.getMessage());
            }
        }

        return constraintViolations.size() == 0;
    }

}
