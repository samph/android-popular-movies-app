# Popular Movies App - Android

Project 1 for the Developing Android Apps Udacity Course

## Progress
* 25/11/18 - Initial Screen and settings done, allowing for sorting by rating, most popular, or now playing.

<img src="https://i.imgur.com/doXlDrm.png" width="200">

* 26/11/18 - Added basic detail Activity.

<img src="https://i.imgur.com/YjhHuxY.png" width="200">


## Requirements

Unfortunately the rubric for assessing this project is no longer online, so I've compiled a list of requirements from the notes and other repositories which make reference to them.

### Basic 

* Present the user with a grid arrangement of movie posters upon launch.
* Allow user to change sort order via setting:
    * Sort by rating
    * Sort by most popular (what is the distinction here?)
* Allow the user to tap on a poster and transition to a detail page with addition information such as:
    * Original title
    * Movie poster thumbnail
    * Plot synopsis
    * User rating
    * Release date

### Advanced

#### Layout
* Movie details page contains:
    * Trailer
    * User Reviews (whose users?)

* Tablet UI:
    * Uses Master-Detail layout implemented using fragments.
        * Left fragment is for 'discovering' movies (grid as normal launch activity?)
        * Right is detail of currently selected movie

#### Functionality
* App uses an intent to launch trailer
* Mark movies as favourites
* Add sort by favourites

#### Networking
* Make good use of API to retrieve relevant details:
    * Top rated
    * Popular
    * Meta data
    * Trailers
    * User reviews

#### Data persistence
* Favourites saved to database using movie ID.
* Favourite setting displays favourites collection

#### Content providers
* App stores favourite movie details in database
* App displays favourite movie details even when offline.
* App uses a ContentProvider to populate favourite movie details.

#### Sharing
* Movie Details view includes an ActionBar item that allows the user to share trailer URL
* App uses a share Intent to share URL.


### Quality
* App conforms to [Udacity Android Developer Nanodegree - Core App Quality Guidelines](http://udacity.github.io/android-nanodegree-guidelines/core.html)
* App conforms to [Android - Core app quality](https://developer.android.com/docs/quality-guidelines/core-app-quality)
* Tests!

