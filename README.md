# GitHub Repo App

Study application made to take advantage of the best programming practices using git hub public api.
Shows a list of github's repositories filtered by kotlin language and order by descending of stars. 

[APK](https://github.com/rafaelmfer/GitHub-Repo-App/blob/master/apk/app-debug.apk?raw=true)
|| [VIDEO](https://github.com/rafaelmfer/GitHub-Repo-App/blob/master/github_assets/videos/screen_recording_app.mp4?raw=true)

<table>
    <thead>
        <tr>
            <th>BASE</th>
            <th>Architecture</th>
            <th>IU</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>AppCompat</td>
            <td>ViewBinding</td>
            <td>Material Components</td>
        </tr>
        <tr>
            <td>Android KTX</td>
            <td>Lifecycles</td>
        </tr>
        <tr>
            <td>Android Arch</td>
            <td>LiveData</td>
        </tr>
        <tr>
            <td>Room</td>
            <td>ViewModel</td>
        </tr>
    </tbody>
</table>


**Screens**
<table>  
    <th>Home Light</th>
    <th>Home Dark</th>
    <tr>
        <td>
            <img src="https://github.com/rafaelmfer/GitHub-Repo-App/blob/master/github_assets/images/Home%20Light.png"/>
        </td>
        <td>
            <img src="https://github.com/rafaelmfer/GitHub-Repo-App/blob/master/github_assets/images/Home%20Dark.png"/>
        </td>
    </tr>
</table>

## Base project

- **Dependency injection:**
  With Koin, a practical dependency injection library, the code will not be coupled and it'll still
  be easy to resolve automatically the dependencies on the runtime and mock them during the tests.

- **Coroutines:**
  With coroutines it is possible to perform asynchronous tasks without changing the code flow of the
  application. Simplifies code by abstracting all the complexity of using threads

- **Room:**
  Room Database is one of the existing libraries within the “Android JetPack” suite, it helps
  developers creating an abstraction of database layers (SQLite) to store information.

- **Kotlin KTS:**
  Using Kotlin KTS we can take advantage of the application configuration using the kotlin language
  in our gradle file. This makes our configuration even easier

## Tests

- **Unit Tests**:

<table>
    <th>HomeViewModelTest</th>
    <tr>
        <td>
            <img src="https://github.com/rafaelmfer/GitHub-Repo-App/blob/master/github_assets/images/HomeViewModelTest.png"/>
        </td>
    </tr>
</table>

- **Instrumented Tests**:

<table>
    <th>GitHubReposDaoTest</th>
    <th>HomeActivityTest</th>
    <tr>
        <td>
            <img src="https://github.com/rafaelmfer/GitHub-Repo-App/blob/master/github_assets/images/GitHubReposDaoTest.png"/>
        </td>
        <td>
            <img src="https://github.com/rafaelmfer/GitHub-Repo-App/blob/master/github_assets/images/HomeActivityTest.png"/>
        </td>
    </tr>
</table>

## Quick start

1. Clone the repository with `git clone https://github.com/rafaelmfer/GitHub-Repo-App.git`
2. Run the application and be happy

## CODE

- **IDE - Android Studio Dolphin 2021.3.1**

- **Gradle 7.3.0**

- **Kotlin 1.7.10**

- **AAC Android Architecture Components** *using guide Google JetPack*

- **MVVM Architecture** *for apply SOLID*

- **ViewBinding** *bind view*

- **Retrofit** *for make the communication to API*

- **Coroutines** *for asynchronous calls and operations*

- **ViewModel** *for interact view with business rules*

- **JUnit / Espresso** *for unit and instrumented tests*

## API

GitHub's API Documentation: https://docs.github.com/en/rest

## DESIGN

**Material Components**

https://github.com/material-components

- MaterialToolbar
- RecyclerView