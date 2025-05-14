import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { PostResponse } from "../api-interfaces/requests/post.response";

@Injectable({
        providedIn: 'root'
})
export class HandlebarService {
    response: PostResponse = {
        posts: [
            {
                dateCreated: new Date(),
                author: "Author1",
                html:`<p>The Date() constructor creates Date objects. When called as a function, it returns a string representing the current time.</p>`
            },
            {
                dateCreated: new Date('1995-12-17T03:24:00'),
                author: "Author2", 
                html:`<p>When no parameters are provided, the newly-created Date object represents the current date and time as of the time of instantiation. The returned date's timestamp is the same as the number returned by Date.now().</p>`
            },
            ...Array.from({ length: 25 }, (_, i) => ({
            dateCreated: new Date(Date.now() - i * 86400000), // each post one day earlier
            author: `Author${i + 3}`,
            html: `<p>This is dummy content for post number ${i + 3}. It describes a fictional scenario with placeholder text.</p>`
            }))
        ],
        total: 27,
        pageNumber: 0,
        pageSize: 10
    }

    getPostsForThread(threadId: number, pageSize: number, pageNumber: number){
        const postStartIndex = pageSize * pageNumber;
        return of({
            posts: this.response.posts.slice(postStartIndex, postStartIndex + pageSize),
            total: this.response.total,
            pageNumber: pageNumber,
            pageSize: pageSize
        })
    }
}