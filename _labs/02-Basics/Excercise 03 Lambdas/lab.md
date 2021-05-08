# Lambdas
## lab time: 45 minutes

### Task 1. Implement sorting by using a lambda
Rewrite the application so the addressbook can be printed alphabetcally on key. The command to output the sorted list is "sort", this should call a function that sorts the HashMap and then prints the sorted list. To sort the entries of the HashMap, you can use this API: book.entries.sortedBy. This function expects a lambda or anonymous function to sort.

### Task 2. Implement filtering by implementing a lambda
Add a filter command that asks the user to enter a filter, then the application should only display the entries that match the given filter in either the key (name) or value (lastname). The results should be printed as output.
The filter predicate should be a function provided to the filterBook function. So the callsite of the filterBook looks like this:
```
"filter" -> {
                print("What are you searching for?")
                val filter: String = readLine()!!
                filterBook {
                    it.key == filter || it.value == filter
                }
            }
```
You have to write the filterBook function to the application