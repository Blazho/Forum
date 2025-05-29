export interface ThreadDTO{
    id: number,
    dateCreated: Date,
    lastDateModified: Date,
    createdBy: String | undefined,
    lastModifiedBy: number | undefined,
    title: String,
    status: String | undefined,
    parentThreadId: number | undefined,
    description: String,
}