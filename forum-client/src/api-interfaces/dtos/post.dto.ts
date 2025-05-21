
export interface Post {
    id: number,
    html: string,
    dateCreated: Date,
    createdBy: string,
    lastDateModified: Date,
    lastModifiedBy: string,
    thread: number
} 