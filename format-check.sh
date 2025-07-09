#java -jar format.jar -a --set-exit-if-changed --skip-sorting-imports -n src/*/*/*/*
find . -name "*.java" -print0 | xargs -0 java -jar format.jar -a --set-exit-if-changed --skip-sorting-imports
