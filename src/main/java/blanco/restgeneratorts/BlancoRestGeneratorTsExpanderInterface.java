package blanco.restgeneratorts;

import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramProcessStructure;
import blanco.restgeneratorts.valueobject.BlancoRestGeneratorTsTelegramStructure;

import java.io.File;

/**
 * Source file Expander interface
 */
public interface BlancoRestGeneratorTsExpanderInterface {
    /**
     * Expand source files
     *
     * @param argProcessStructure
     * @param argDirectoryTarget
     */
    public void expand(
            final BlancoRestGeneratorTsTelegramProcessStructure argProcessStructure,
            final File argDirectoryTarget
    );

    /**
     * Generates a telegram class.
     *
     * @param argTelegramStructure
     * @param argDirectoryTarget
     */
    public void generateTelegram(
            final BlancoRestGeneratorTsTelegramStructure argTelegramStructure,
            final File argDirectoryTarget);
}
