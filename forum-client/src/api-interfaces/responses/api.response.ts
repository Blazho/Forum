
export interface ApiResponse<data>{
    data: data,
    error: boolean | undefined,
    errorMessage: string | undefined
}