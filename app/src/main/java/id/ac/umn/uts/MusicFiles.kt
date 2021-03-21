package id.ac.umn.uts

class MusicFiles {
    var path: String? = null
        get() = field
        set(value) {
            field = value
        }
    var title: String? = null
        get() = field
        set(value) {
            field = value
        }
    var album: String? = null
        get() = field
        set(value) {
            field = value
        }
    var duration: String? = null
        get() = field
        set(value) {
            field = value
        }


    constructor(path: String?, title: String?, album: String?, duration: String?) {
        this.path = path
        this.title = title
        this.album = album
        this.duration = duration
    }

}
