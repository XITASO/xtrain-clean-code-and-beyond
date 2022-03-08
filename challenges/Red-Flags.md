# Red Flags

## Shallow Module
The interface for a class or method isn't much simpler than its implementation.

<details>
<summary>Example</summary>

```java
public interface ILinkedList<T> {
    void add(T item);
    void remove(T item);
}

public class LinkedList<T> implements ILinkedList<T> {
    public void add(T item) {
        //...
    }
    public void remove(T item) {
        //...
    }
}
```
</details>

## Information Leakage
A design decision is reflected in multiple modules.

<details>
<summary>Example</summary>

```java
public class CsvReader {
    public MyDataClass read() {
        // read column n to member m
    }
}

public class CsvWriter {
    public void write(MyDataClass content) {
        // write member m to column n 
    }
}

public static void Main(String[] args) {
    String content = new CsvReader().read();
    // ToDo change content
    new CsvWriter().write(content);
}
```
</details>

## Temporal Decomposition (a form of information leakage)
The code structure is based on the order in which operations are executed, not on information hiding.

> Read HTTP request ➡ Parse HTTP request <br>
> ⚡ To read it correctly you have to, at least partially, parse it.


## Overexposure
An API forces callers to be aware of rarely used features in order to use commonly used features.

<details>
<summary>Example</summary>

```java
public string getTranslation (String key, String languageISOCode){
    if ( translator.containsKey(key) ) {
        Translation translation = translator.get(key);
        if ( translation.canTranslateTo(languageISOCode) ) {
            return translation.getLanguageSpecificTranslation(languageISOCode);
        }
        return "Not found";
    }
    return "Not found";
}

public string getBetterTranslation (String key, String languageISOCode){
    if ( languageISOCode.isEmpty() ) {
        languageISOCode = Thread.currentThread().getCurrentLanguage().getISOCode();
    }
    if ( translator.containsKey(key) ) {
        Translation translation = translator.get(key);
        if ( translation.canTranslateTo(languageISOCode) ) {
            return translation.getLanguageSpecificTranslation(languageISOCode);
        }
        return "Not found";
    }
    return "Not found";
}

public string getBetterTranslation (String key) {
    return getBetterTranslation(key, Thread.currentThread().getCurrentLanguage().getISOCode());
}   
```
</details>

> Implement good default for parameters! <br>
> Classes or methods should do "the right thing" by default.

## Pass-Through Method
A pass-through method is one that does nothing except pass its arguments to another method, usually with the same API as the pass-through method. This typically indicates that there is not a clean division of responsibility between the classes.

<details>
<summary>Example</summary>

```java
public class TranslationService {

    public String getTranslation (String key) {
        return translator.getTranslation(key);
    }
}
```
</details>

## Repetition
If the same piece of code (or code that is almost the same) appears over and over again, that's a red flag that you haven't found the right abstractions.

> Extract code to methods or modules.

## Special-General Mixture
This red flag occures when a general-purpose mechanism also contains code specialized for a particular use of that mechanism. This makes the mechanism more complicated and creates information leakage between the mechanism and the particular use case: future modifications to the use case are likely to require changes to the underlying mechanism as well.

<details>
<summary>Example</summary>

```java
public static class NetworkErrorLogger {
    
    public static void logRpcOpenError (RpcRequest req, Exception e) {
        logger.log(Level.WARNING, "Error opening a rpc connection. Message: " + e);
    }
    public static void logRpcCloseError (RpcRequest req, Exception e) {
        logger.log(Level.ERROR, "Error closing a rpc connection. Message: " + e);
    }
    //...
}
```
</details>

> You have to look at the code, to find out what is actually happening and if it's correct. <br>
> 


## Conjoined Method
It should be possible to understand each method independently. If you can't understand the implementation of one method without also understanding the implementation of another, that's a red flag. This red flag can occur in other contexts as well: if two pieces of code are physicallyy separated, but each can only be understood by looking at the other, that is a red flag.

<details>
<summary>Example</summary>

```java
public class CarwashTicketing {

    public Set<string> GetOnetimeCodes (Request req) {
        if ( !Validate(req) ) {
            throw Error("Not allowed");
        }
        FetchResult result = fetchOnetimeCodes(req.Data);
        if ( result.length() == 0 ) {
            throw Error("Could not create tickets");
        }
        return result.getTickets();
    }

    private FetchResult fetchOnetimeCodes(RequestData data) {
        checkExistingData();
        createTickets();
        //...
        return result;
    }
}
```
</details>

## Comment Repeats Code
All of the information in a comment is immediately obvious from the code next to the comment.

<details>
<summary>Example</summary>

```java
/*
 * Obtain a normalized resource name from REQ.
 */
private static String[] getNormalizedResourceName(HTTPRequest req) {
    // ...
}
```
</details>

## Implementation Documentation Contaminates Interface
An interface comment describes implementation details not needed by users of the thing being documented.

> Don't overload the user of the api with unnecessary information.


## Vague Name
The name of a variable or method is so imprecise that it doesn't convey much useful information and may lead to misuse.

<details>
<summary>Example</summary>

```java
public class ProjectManager {

    public int getCount() {
        //...
    }
}

public class BetterProjectManager {

    public int getNumberOfProjects() {
        //...
    }
}
```
</details>


## Hard to Pick Name
It is difficult to come up with a precise and intuitive name for a variable or method.

> Think about if this method or variable really does the right thing.

## Hard to Describe
The comment that describes a method or variable should be simple and yet complete.

> If you find it difficult to write such a comment, something with your method or variable is wrong.

## Nonobvious Code
The behavior or meaning of a piece of code cannot be understood easily.

<details>
<summary>Example</summary>

```java
public static void main(String[] args) {
    //...
    new RaftClient(myAddress, serverAddresses);
}
```

<details>
<summary>Details</summary>

```java
public class RaftClient {
    public RaftClient(String address, String[] serverAddresses) {
        // to keep the application running
        Thread.createThread();
    }
}
```
</details>

</details>

> Software should be designed for ease of reading, not ease of writing!