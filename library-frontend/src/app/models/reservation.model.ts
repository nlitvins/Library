export interface Reservation {
  id: number;
  userId: number;
  bookId: number;
  createdDate: string;
  termDate: string;
  updatedDate: string;
  status: string;
  extensionCount: number;
}

export interface ReservationDetailed {
  reservation: Reservation;
  book: {
    id: number;
    title: string;
    author: string;
    quantity: number;
  };
  user: {
    id: number;
    name: string;
    secondName: string;
    email: string;
  };
}
