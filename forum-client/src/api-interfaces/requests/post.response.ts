import { Post } from "../dtos/post.dto"

export interface PostResponse{
    posts: Post[]
    total: number,
    pageNumber: number,
    pageSize: number
}