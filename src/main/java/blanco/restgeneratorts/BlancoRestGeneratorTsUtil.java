package blanco.restgeneratorts;

import blanco.cg.BlancoCgSupportedLang;
import blanco.restgeneratorts.resourcebundle.BlancoRestGeneratorTsResourceBundle;
import blanco.restgeneratorts.task.valueobject.BlancoRestGeneratorTsProcessInput;
import blanco.valueobjectts.BlancoValueObjectTsXmlParser;
import blanco.valueobjectts.valueobject.BlancoValueObjectTsClassStructure;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Gets the list of Object created by BlancoValueObject from XML and keeps it.
 *
 * Created by tueda on 15/07/05.
 */
public class BlancoRestGeneratorTsUtil {
    /**
     * An access object to the resource bundle for ValueObject.
     */
    private final static BlancoRestGeneratorTsResourceBundle fBundle = new BlancoRestGeneratorTsResourceBundle();

    public static Map<String, Integer> mapCommons = new HashMap<String, Integer>() {
        {put(fBundle.getMeta2xmlElementCommon(), BlancoCgSupportedLang.JAVA);}
        {put(fBundle.getMeta2xmlElementCommonCs(), BlancoCgSupportedLang.CS);}
        {put(fBundle.getMeta2xmlElementCommonJs(), BlancoCgSupportedLang.JS);}
        {put(fBundle.getMeta2xmlElementCommonVb(), BlancoCgSupportedLang.VB);}
        {put(fBundle.getMeta2xmlElementCommonPhp(), BlancoCgSupportedLang.PHP);}
        {put(fBundle.getMeta2xmlElementCommonRuby(), BlancoCgSupportedLang.RUBY);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.PYTHON);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.KOTLIN);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.TS);}
    };

    static public boolean isVerbose = false;
    public static boolean isTelegramStyleBlanco = true;
    public static boolean isTelegramStylePlain = false;

    public static HashMap<String, BlancoValueObjectTsClassStructure> objects = new HashMap<>();

    static public void processValueObjects(final BlancoRestGeneratorTsProcessInput input) throws IOException {
        if (isVerbose) {
            System.out.println("BlancoRestGeneratorTsObjectsInfo : processValueObjects start !");
        }

        /* tmpdir is unique. */
        String baseTmpdir = input.getTmpdir();
        /* searchTmpdir is comma separated. */
        String tmpTmpdirs = input.getSearchTmpdir();
        List<String> searchTmpdirList = null;
        if (tmpTmpdirs != null && !tmpTmpdirs.equals(baseTmpdir)) {
            String[] searchTmpdirs = tmpTmpdirs.split(",");
            searchTmpdirList = new ArrayList<>(Arrays.asList(searchTmpdirs));
        }
        if (searchTmpdirList == null) {
            searchTmpdirList = new ArrayList<>();
        }
        searchTmpdirList.add(baseTmpdir);

        for (String tmpdir : searchTmpdirList) {
            searchTmpdir(tmpdir.trim());
        }
    }

    static private void searchTmpdir(String tmpdir) {

        // Reads information from XML-ized intermediate files.
        final File[] fileMeta3 = new File(tmpdir
                + BlancoRestGeneratorTsConstants.OBJECT_SUBDIRECTORY)
                .listFiles();

        if (fileMeta3 == null) {
            System.out.println("!!! NO FILES in " + tmpdir
                    + BlancoRestGeneratorTsConstants.OBJECT_SUBDIRECTORY);
            throw new IllegalArgumentException("!!! NO FILES in " + tmpdir
                    + BlancoRestGeneratorTsConstants.OBJECT_SUBDIRECTORY);
        }

        for (int index = 0; index < fileMeta3.length; index++) {
            if (fileMeta3[index].getName().endsWith(".xml") == false) {
                continue;
            }

            BlancoValueObjectTsXmlParser parser = new BlancoValueObjectTsXmlParser();
//            parser.setVerbose(this.isVerbose());
            /*
             * First, it searches all the sheets and make a list of class and package names.
             * This is because in the PHP-style definitions, the package name is not specified when specifying class.
             */
//            BlancoValueObjectTsXmlParser.classList =
//                    BlancoValueObjectTsXmlParser.createClassListFromSheets(fileMeta3);
            final BlancoValueObjectTsClassStructure[] structures = parser.parse(fileMeta3[index]);

            if (structures != null ) {
                for (int index2 = 0; index2 < structures.length; index2++) {
                    BlancoValueObjectTsClassStructure structure = structures[index2];
                    if (structure != null) {
                        if (isVerbose) {
                            System.out.println("processValueObjects: " + structure.getName());
                        }
                        objects.put(structure.getName(), structure);
                    } else {
                        System.out.println("processValueObjects: a structure is NULL!!!");
                    }
                }
            } else {
                System.out.println("processValueObjects: structures are NULL!!!");
            }
        }
    }

    /**
     * Returns the parent class of the telegram for each method.
     * @param method
     * @return
     */
    static public String getDefaultRequestTelegramId(String method) {
        String telegramId = null;

        if (method == null) {
            throw new IllegalArgumentException("!!! METHOD CANNOT BE NULL !!! " + method);
        }

        if (BlancoRestGeneratorTsConstants.HTTP_METHOD_GET.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_GET_REQUESTID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_POST.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_POST_REQUESTID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_PUT.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_PUT_REQUESTID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_DELETE.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_DELETE_REQUESTID;
        } else {
            throw new IllegalArgumentException("!!! NO SUCH METHOD !!! " + method);
        }

        return telegramId;
    }

    /**
     *Returns the parent class of the telegram for each method.
     * @param method
     * @return
     */
    static public String getDefaultResponseTelegramId(String method) {
        String telegramId = null;

        if (method == null) {
            throw new IllegalArgumentException("!!! METHOD CANNOT BE NULL !!! " + method);
        }

        if (BlancoRestGeneratorTsConstants.HTTP_METHOD_GET.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_GET_RESPONSEID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_POST.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_POST_RESPONSEID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_PUT.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_PUT_RESPONSEID;
        } else if (BlancoRestGeneratorTsConstants.HTTP_METHOD_DELETE.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorTsConstants.DEFAULT_API_DELETE_RESPONSEID;
        } else {
            throw new IllegalArgumentException("!!! NO SUCH METHOD !!! " + method);
        }

        return telegramId;
    }

    /**
     * Make canonical classname into Simple.
     *
     * @param argClassNameCanon
     * @return simpleName
     */
    static public String getSimpleClassName(final String argClassNameCanon) {
        if (argClassNameCanon == null) {
            return "";
        }

        String simpleName = "";
        final int findLastDot = argClassNameCanon.lastIndexOf('.');
        if (findLastDot == -1) {
            simpleName = argClassNameCanon;
        } else if (findLastDot != argClassNameCanon.length() - 1) {
            simpleName = argClassNameCanon.substring(findLastDot + 1);
        }
        return simpleName;
    }

    /**
     * Make canonical classname into packageName
     *
     * @param argClassNameCanon
     * @return
     */
    static public String getPackageName(final String argClassNameCanon) {
        if (argClassNameCanon == null) {
            return "";
        }

        String simpleName = "";
        final int findLastDot = argClassNameCanon.lastIndexOf('.');
        if (findLastDot > 0) {
            simpleName = argClassNameCanon.substring(0, findLastDot);
        }
        return simpleName;
    }

    /**
     * Creates import statements.
     * @param argPackageName
     * @param argClassName
     * @param argImportHeaderList
     * @param argBasedir
     * @param argTelegramPackage
     */
    static public void makeImportHeaderList(
            final String argPackageName,
            final String argClassName,
            final Map<String, List<String>> argImportHeaderList,
            final String argBasedir,
            final String argTelegramPackage) {
        if (argClassName == null || argClassName.length() == 0) {
            System.out.println("BlancoRestGeneratorTsXmlParser#makeImportHeaderList className is not specified. SKIP.");
            return;
        }
        String importFrom = "./" + argClassName;
        if (argPackageName != null &&
                argPackageName.length() != 0 &&
                argPackageName.equals(argTelegramPackage) != true) {
            String classNameCanon = argPackageName.replace('.', '/') + "/" + argClassName;

            String basedir = "";
            if (argBasedir != null) {
                basedir = argBasedir;
            }
            importFrom = basedir + "/" + classNameCanon;
        }

        List<String> importClassList = argImportHeaderList.get(importFrom);
        if (importClassList == null) {
            importClassList = new ArrayList<>();
            argImportHeaderList.put(importFrom, importClassList);
        }
        boolean isMatch = false;
        for (String myClass : importClassList) {
            if (argClassName.equals(myClass)) {
                isMatch = true;
                break;
            }
        }
        if (!isMatch) {
            importClassList.add(argClassName);
            if (isVerbose) {
                System.out.println("BlancoRestGeneratorTsXmlParser#makeImportHeaderList: new import { " + argClassName + " } from \"" + importFrom + "\"");
            }
        }
    }
}
