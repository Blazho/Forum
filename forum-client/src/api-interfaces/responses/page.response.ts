import { Pageable } from "../dtos/pageable.dts"

export interface PageResponse<Content>{
    content: Content[],
    totalElements: number,
    pageable: Pageable
}