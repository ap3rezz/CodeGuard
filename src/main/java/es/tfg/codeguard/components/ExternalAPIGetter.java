package es.tfg.codeguard.components;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.service.ExternalAPIService;

@Component
public class ExternalAPIGetter {

    private static final int N_EXERCISES = 10;

    @Autowired
    private ExternalAPIService codeWarsAPIService;
    @Autowired
    private ExternalAPIService projectEulerAPIService;
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Bean
    void getExercisesFromCodeWars() {

        Exercise magicMusicBoxExercise = new Exercise(codeWarsAPIService.requestExerciseById("magic-music-box"));

        exerciseRepository.save(new Exercise(codeWarsAPIService.requestExerciseById("valid-braces")));
        exerciseRepository.save(setMagicMusicBoxTests(magicMusicBoxExercise));
    }

    @Bean
    void getNExercisesFromProjectEuler() {
        
        for (ExerciseDTO exercise : projectEulerAPIService.requestNExercises(N_EXERCISES))
            exerciseRepository.save(new Exercise(exercise));
    }

    private Exercise setMagicMusicBoxTests(Exercise magicMusicBoxExercise){
        magicMusicBoxExercise.setTest("import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                "import org.junit.jupiter.api.RepeatedTest;\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "\n" +
                "import java.util.Arrays;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Map;\n" +
                "import java.util.Random;\n" +
                "import java.util.LinkedHashSet;\n" +
                "import java.util.Set;\n" +
                "import java.util.stream.Stream;\n" +
                "\n" +
                "public class MagicMusicBoxTest {\n" +
                "\n" +
                "\t@Test\n" +
                "\tvoid emptyInputTest() {\n" +
                "\n" +
                "\t\tString[] expectedWords = new String[] {};\n" +
                "\t\tString[] inputWords = new String[] {};\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t}\n" +
                "\n" +
                "\t@Test\n" +
                "\tvoid sampleTestSimpleCase() {\n" +
                "\n" +
                "\t\tString[] expectedWords = new String[] { \"DOWN\", \"REPTILE\", \"AMIDST\", \"SOFA\" };\n" +
                "\t\tString[] inputWords = new String[] { \"DOWN\", \"REPTILE\", \"AMIDST\", \"SOFA\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\t}\n" +
                "\n" +
                "\t@Test\n" +
                "\tvoid sampleTestCase() {\n" +
                "\n" +
                "\t\tString[] expectedWords = new String[] { \"DOWN\", \"REPTILE\", \"AMIDST\", \"SOFA\", \"SOLAR\", \"PLANE\", \"SILENCE\",\n" +
                "\t\t\t\t\"MARKDOWN\" };\n" +
                "\t\tString[] inputWords = new String[] { \"DOWN\", \"PLANE\", \"AMIDST\", \"REPTILE\", \"SOFA\", \"SOLAR\", \"SILENCE\", \"DOWN\",\n" +
                "\t\t\t\t\"MARKDOWN\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t}\n" +
                "\n" +
                "\t@Test\n" +
                "\tvoid duplicateWordsTest() {\n" +
                "\n" +
                "\t\tString[] expectedWords = new String[] { \"DOOR\", \"REPTILE\", \"SIMILAR\", \"SOFA\", \"DISSOLVED\", \"LAPTOP\", \"RESIST\" };\n" +
                "\t\tString[] inputWords = new String[] { \"DOOR\", \"DOOR\", \"REPTILE\", \"REPTILE\", \"SIMILAR\", \"SIMILAR\", \"SOFA\", \"SOFA\",\n" +
                "\t\t\t\t\"DISSOLVED\", \"DISSOLVED\", \"LAPTOP\", \"LAPTOP\", \"RESIST\", \"RESIST\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t}\n" +
                "\t\n" +
                "\t  @Test\n" +
                "\t\tvoid sampleTestWrongShiftCase() {\n" +
                "\n" +
                "\t\t\tString[] expectedWords = new String[] { \"DOWN\"};\n" +
                "\t\t\tString[] inputWords = new String[] { \"DOWN\", \"AMIDST\", \"SOFA\", \"FACTION\"};\n" +
                "\n" +
                "\t\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)), magicMessage(inputWords));\n" +
                "\n" +
                "\t\t}\n" +
                "\n" +
                "\t@Test\n" +
                "\tvoid fixedTest() {\n" +
                "\n" +
                "\t\tString[] expectedWords = new String[] {};\n" +
                "\t\tString[] inputWords = new String[] { \"PLANE\", \"AMIDST\", \"REPTILE\", \"SOFA\", \"SOLAR\", \"FLOOR\", \"SIMILAR\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t\texpectedWords = new String[] { \"DOWN\" };\n" +
                "\t\tinputWords = new String[] { \"TABLE\", \"LAPTOP\", \"DOWN\", \"CAR\", \"MOUSE\", \"DINNER\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t\texpectedWords = new String[] { \"DOWN\", \"CORRECT\" };\n" +
                "\t\tinputWords = new String[] { \"RECORD\", \"LAPTOP\", \"DOWN\", \"CAR\", \"MOUSE\", \"DINNER\", \"CORRECT\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t\texpectedWords = new String[] { \"DOWN\", \"CORRECT\", \"COMIC\" };\n" +
                "\t\tinputWords = new String[] { \"RECORD\", \"COMIC\", \"LAPTOP\", \"DOWN\", \"CAR\", \"MOUSE\", \"DINNER\", \"CORRECT\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t\texpectedWords = new String[] { \"DOWN\", \"CORRECT\", \"COMIC\", \"FAMILY\" };\n" +
                "\t\tinputWords = new String[] { \"RECORD\", \"COMIC\", \"LAPTOP\", \"DOWN\", \"CAR\", \"FAMILY\", \"MOUSE\", \"DINNER\",\n" +
                "\t\t\t\t\"CORRECT\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t\texpectedWords = new String[] { \"DOWN\", \"CORRECT\", \"COMIC\", \"FAMILY\", \"DISSOLVED\" };\n" +
                "\t\tinputWords = new String[] { \"RECORD\", \"COMIC\", \"DOWN\", \"CAR\", \"FAMILY\", \"MOUSE\", \"DISSOLVED\", \"DINNER\",\n" +
                "\t\t\t\t\"CORRECT\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t\texpectedWords = new String[] { \"DOWN\", \"CORRECT\", \"COMIC\", \"FAMILY\", \"DISSOLVED\", \"LAPTOP\" };\n" +
                "\t\tinputWords = new String[] { \"RECORD\", \"COMIC\", \"LAPTOP\", \"SYLLABLE\", \"DOWN\", \"CAR\", \"FAMILY\", \"MOUSE\",\n" +
                "\t\t\t\t\"DISSOLVED\", \"DINNER\", \"CORRECT\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t\texpectedWords = new String[] { \"DOWN\", \"CORRECT\", \"COMIC\", \"FAMILY\", \"DISSOLVED\", \"LAPTOP\", \"RESIST\" };\n" +
                "\t\tinputWords = new String[] { \"RECORD\", \"RESIST\", \"COMIC\", \"LAPTOP\", \"SYLLABLE\", \"DOWN\", \"CAR\", \"FAMILY\", \"MOUSE\",\n" +
                "\t\t\t\t\"DISSOLVED\", \"DINNER\", \"CORRECT\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t\texpectedWords = new String[] { \"DOOR\", \"RECORD\", \"COMIC\", \"FAMILY\", \"DISSOLVED\", \"LAPTOP\", \"RESIST\", \"DOWN\",\n" +
                "\t\t\t\t\"CORRECT\" };\n" +
                "\t\tinputWords = new String[] { \"DOOR\", \"RECORD\", \"RESIST\", \"COMIC\", \"LAPTOP\", \"SYLLABLE\", \"DOWN\", \"CAR\", \"FAMILY\",\n" +
                "\t\t\t\t\"MOUSE\", \"DISSOLVED\", \"DINNER\", \"CORRECT\" };\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\t}\n" +
                "\n" +
                "\t@RepeatedTest(1000)\n" +
                "\tvoid randomTest() {\n" +
                "\n" +
                "\t\tString[] inputWords = generateRandomWordsArray();\n" +
                "\t\tString[] expectedWords = magicResolution(inputWords);\n" +
                "\n" +
                "\t\tassertEquals(Arrays.toString(expectedWords), Arrays.toString(MagicMusicBox.magicMusicBox(inputWords)),\n" +
                "\t\t\t\tmagicMessage(inputWords));\n" +
                "\n" +
                "\t}\n" +
                "\n" +
                "\tprivate String[] magicResolution(String[] inputWords) {\n" +
                "\t\tif (inputWords == null || inputWords.length == 0) {\n" +
                "\t\t\treturn new String[] {};\n" +
                "\t\t}\n" +
                "\t\tString[] notes = new String[] { \"DO\", \"RE\", \"MI\", \"FA\", \"SOL\", \"LA\", \"SI\" };\n" +
                "\t\tString[] filteredWords = Stream.of(inputWords)\n" +
                "\t\t\t\t.filter(w -> w.matches(\".*(?:DO|RE|MI|FA|SOL|LA|SI).*\"))\n" +
                "\t\t\t\t.toArray(String[]::new);\n" +
                "\t\tSet<String> solvedWords = new LinkedHashSet<>();\n" +
                "\t\tboolean searching = true;\n" +
                "\t\tint noteCount = 0;\n" +
                "\t\twhile (searching) {\n" +
                "\t\t\tsearching = false;\n" +
                "\t\t\tfor (String word : filteredWords) {\n" +
                "\t\t\t\tif (word.contains(notes[noteCount]) && solvedWords.add(word)) {\n" +
                "\t\t\t\t\tsearching = true;\n" +
                "\t\t\t\t\tnoteCount = (++noteCount)%notes.length;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t\treturn solvedWords.toArray(new String[0]);\n" +
                "\t}\n" +
                "\n" +
                "\tprivate static String[] generateRandomWordsArray() {\n" +
                "\n" +
                "\t\tMap<Integer, String> musicalWords = new HashMap<>() {\n" +
                "\t\t\t{\n" +
                "\t\t\t\tput(1, \"SOLAR\");\n" +
                "\t\t\t\tput(2, \"DREAMER\");\n" +
                "\t\t\t\tput(3, \"FACTOR\");\n" +
                "\t\t\t\tput(4, \"FARMERS\");\n" +
                "\t\t\t\tput(5, \"DOCTOR\");\n" +
                "\t\t\t\tput(6, \"CREDIBLE\");\n" +
                "\t\t\t\tput(7, \"MIRACLE\");\n" +
                "\t\t\t\tput(8, \"SIMPLIFY\");\n" +
                "\t\t\t\tput(9, \"ENDOW\");\n" +
                "\t\t\t\tput(10, \"MISTAKE\");\n" +
                "\t\t\t\tput(11, \"CORRECT\");\n" +
                "\t\t\t\tput(12, \"SOLUTION\");\n" +
                "\t\t\t\tput(13, \"ADORABLE\");\n" +
                "\t\t\t\tput(14, \"LANDSCAPE\");\n" +
                "\t\t\t\tput(15, \"SALARY\");\n" +
                "\t\t\t\tput(16, \"COMMIT\");\n" +
                "\t\t\t\tput(17, \"CLARITY\");\n" +
                "\t\t\t\tput(18, \"BUFFALO\");\n" +
                "\t\t\t\tput(19, \"DESOLATE\");\n" +
                "\t\t\t\tput(20, \"BASIC\");\n" +
                "\t\t\t\tput(21, \"EROSION\");\n" +
                "\t\t\t\tput(22, \"ENVIRONMENT\");\n" +
                "\t\t\t\tput(23, \"HORIZON\");\n" +
                "\t\t\t\tput(24, \"EXAMPLE\");\n" +
                "\t\t\t\tput(25, \"HELICOPTER\");\n" +
                "\t\t\t\tput(26, \"INNOVATION\");\n" +
                "\t\t\t\tput(27, \"JOURNEY\");\n" +
                "\t\t\t\tput(28, \"KITCHEN\");\n" +
                "\t\t\t\tput(29, \"LIBRARY\");\n" +
                "\t\t\t\tput(30, \"MOUNTAIN\");\n" +
                "\t\t\t\tput(31, \"PERMITS\");\n" +
                "\t\t\t\tput(32, \"FANTASTIC\");\n" +
                "\t\t\t\tput(33, \"FACTORIAL\");\n" +
                "\t\t\t\tput(34, \"CREDIBLE\");\n" +
                "\t\t\t\tput(35, \"DOLLY\");\n" +
                "\t\t\t\tput(36, \"DOBLE\");\n" +
                "\t\t\t\tput(37, \"RELATION\");\n" +
                "\t\t\t\tput(38, \"DODGE\");\n" +
                "\t\t\t\tput(39, \"FREEDOM\");\n" +
                "\t\t\t\tput(40, \"RIVER\");\n" +
                "\t\t\t\tput(41, \"MIMICRY\");\n" +
                "\t\t\t\tput(42, \"MONTAIN\");\n" +
                "\t\t\t\tput(43, \"COLLABORATE\");\n" +
                "\t\t\t\tput(44, \"REACTOR\");\n" +
                "\t\t\t\tput(45, \"CLOUD\");\n" +
                "\t\t\t\tput(46, \"FISH\");\n" +
                "\t\t\t\tput(47, \"SUNSHINE\");\n" +
                "\t\t\t\tput(48, \"LATERAL\");\n" +
                "\t\t\t\tput(49, \"SOLAR\");\n" +
                "\t\t\t\tput(50, \"RELATE\");\n" +
                "\t\t\t\tput(51, \"CHAIR\");\n" +
                "\t\t\t\tput(52, \"BOOK\");\n" +
                "\t\t\t\tput(53, \"SADOMASOCHISM\");\n" +
                "\t\t\t\tput(54, \"FAIRYTALE\");\n" +
                "\t\t\t\tput(55, \"PREPARE\");\n" +
                "\t\t\t\tput(56, \"WINDOW\");\n" +
                "\t\t\t\tput(57, \"FACILITATE\");\n" +
                "\t\t\t\tput(58, \"HOUSE\");\n" +
                "\t\t\t\tput(59, \"ENDORSE\");\n" +
                "\t\t\t\tput(60, \"GARDEN\");\n" +
                "\t\t\t\tput(61, \"MODIFY\");\n" +
                "\t\t\t\tput(62, \"TREATMENT\");\n" +
                "\t\t\t\tput(63, \"PARK\");\n" +
                "\t\t\t\tput(64, \"RESOLUTE\");\n" +
                "\t\t\t\tput(65, \"CONSOLIDATE\");\n" +
                "\t\t\t\tput(66, \"NIGHT\");\n" +
                "\t\t\t\tput(67, \"BREEDER\");\n" +
                "\t\t\t\tput(68, \"SINGULAR\");\n" +
                "\t\t\t\tput(69, \"BEDROOM\");\n" +
                "\t\t\t\tput(70, \"ABSOLUTE\");\n" +
                "\t\t\t\tput(71, \"VISIBLE\");\n" +
                "\t\t\t\tput(72, \"REINFORCE\");\n" +
                "\t\t\t\tput(73, \"REMINDER\");\n" +
                "\t\t\t\tput(74, \"FAITHFUL\");\n" +
                "\t\t\t\tput(75, \"COMIC\");\n" +
                "\t\t\t\tput(76, \"VISIBLE\");\n" +
                "\t\t\t\tput(77, \"ELABORATE\");\n" +
                "\t\t\t\tput(78, \"PREDOMINATE\");\n" +
                "\t\t\t\tput(79, \"CONSISTENT\");\n" +
                "\t\t\t\tput(80, \"RESIST\");\n" +
                "\t\t\t\tput(81, \"KITCHEN\");\n" +
                "\t\t\t\tput(82, \"MINIMAL\");\n" +
                "\t\t\t\tput(83, \"GATE\");\n" +
                "\t\t\t\tput(84, \"PLATFORM\");\n" +
                "\t\t\t\tput(85, \"FANATIC\");\n" +
                "\t\t\t\tput(86, \"ADMISSION\");\n" +
                "\t\t\t\tput(87, \"SILENT\");\n" +
                "\t\t\t\tput(88, \"SOLUBLE\");\n" +
                "\t\t\t\tput(89, \"FARMLAND\");\n" +
                "\t\t\t\tput(90, \"GLACIAL\");\n" +
                "\t\t\t\tput(91, \"ROW\");\n" +
                "\t\t\t\tput(92, \"EMIGRANT\");\n" +
                "\t\t\t\tput(93, \"SISTER\");\n" +
                "\t\t\t\tput(94, \"SOLSTICE\");\n" +
                "\t\t\t\tput(95, \"SITUATION\");\n" +
                "\t\t\t\tput(96, \"WIND\");\n" +
                "\t\t\t\tput(97, \"OCEAN\");\n" +
                "\t\t\t\tput(98, \"SOLID\");\n" +
                "\t\t\t\tput(99, \"DORMITORY\");\n" +
                "\t\t\t\tput(100, \"FOUNTAIN\");\n" +
                "\t\t\t}\n" +
                "\t\t};\n" +
                "\n" +
                "\t\tRandom random = new Random();\n" +
                "\t\tint size = random.nextInt(100) + 1;\n" +
                "\n" +
                "\t\tString[] wordsArray = new String[size];\n" +
                "\n" +
                "\t\tfor (int i = 0; i < size; i++) {\n" +
                "\t\t\tint randomKey = random.nextInt(musicalWords.size()) + 1;\n" +
                "\t\t\twordsArray[i] = musicalWords.get(randomKey);\n" +
                "\t\t}\n" +
                "\n" +
                "\t\treturn wordsArray;\n" +
                "\t}\n" +
                "\n" +
                "\tprivate static String magicMessage(String[] inputWords) {\n" +
                "\n" +
                "\t\treturn String.format(\"For input: «%s» \", Arrays.toString(inputWords));\n" +
                "\n" +
                "\t}\n" +
                "\n" +
                "}\n" +
                "\n");
        magicMusicBoxExercise.setPlaceholder("public class MagicMusicBox {\n" +
                "  \n" +
                "  public static String[] magicMusicBox(String[] words) {\n" +
                "    \n" +
                "    // DO YOUR MAGIC HERE\n" +
                "    return new String[] {};\n" +
                "  }\n" +
                "  \n" +
                "}");

        return magicMusicBoxExercise;
    }

}
