export interface PermissionDictionary{
    [key: string]: PermissionLayer
}

export enum PermissionLayer{
    NONE = 0,
    VIEW = 1,
    CREATE = 2,
    DELETE = 3,
    EDIT = 4,
}

export interface LoginResponse{
    token: string,
    userPermissions: PermissionDictionary
}

export enum PermissionName{
    PROMOTE_USER_PERMISSION = "PROMOTE_USER_PERMISSION",
    POST_PERMISSION = "POST_PERMISSION",
    THREAD_PARENT_PERMISSION = "THREAD_PARENT_PERMISSION",
    THREAD_CHILD_PERMISSION= "THREAD_CHILD_PERMISSION",
}