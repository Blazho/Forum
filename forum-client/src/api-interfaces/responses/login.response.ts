export interface PermissionDictionary{
    [key: string]: PermissionLayer
}

export enum PermissionLayer{
    NONE,
    VIEW,
    CREATE,
    DELETE,
    EDIT,
}

export interface LoginResponse{
    token: string,
    userPermissions: PermissionDictionary
}