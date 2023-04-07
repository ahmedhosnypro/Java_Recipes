package recipes.dto;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

/**
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
public class Dto {
    private Dto() {
    }

    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
