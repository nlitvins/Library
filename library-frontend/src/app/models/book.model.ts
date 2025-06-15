export enum BookStatus {
  AVAILABLE = 'AVAILABLE',
  NOT_AVAILABLE = 'NOT_AVAILABLE'
}

export enum BookGenre {
  ROMANCE = 'ROMANCE',
  MODERNIST = 'MODERNIST',
  CLASSIC = 'CLASSIC',
  MAGICAL_REALISM = 'MAGICAL_REALISM',
  DYSTOPIAN = 'DYSTOPIAN',
  FANTASY = 'FANTASY',
  ADVENTURE = 'ADVENTURE',
  PHILOSOPHICAL = 'PHILOSOPHICAL',
  ANCIENT = 'ANCIENT',
  RELIGIOUS = 'RELIGIOUS'
}

export enum BookType {
  BOOK = 'BOOK',
  MAGAZINE = 'MAGAZINE',
  NEWSPAPER = 'NEWSPAPER'
}

export interface Book {
  id: number;
  title: string;
  author: string;
  quantity: number;
  creationYear: string;
  status: BookStatus;
  genre: BookGenre;
  pages: number;
  edition: string;
  releaseDate: string;
  type: BookType;
} 