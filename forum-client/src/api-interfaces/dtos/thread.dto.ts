export interface ThreadDTO{
    id?: number,
    dateCreated?: Date,
    lastDateModified?: Date,
    createdBy?: number,
    createdByUsername?: String,
    lastModifiedBy?: number,
    lastModifiedByUsername?: String,
    title?: String,
    status?: String,
    parentThreadId?: number,
    description?: String,
}