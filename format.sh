#java -jar format.jar -i -a --skip-sorting-imports src/*/*/*/*
find . -name "*.java" -print0 | xargs -0 java -jar format.jar -i -a --skip-sorting-imports
