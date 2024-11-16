export interface Question {
    id: number;
    query: string;
    response: string;
    queryDate: Date;
    responseDate: Date;
    clientId: number;
    healthProfessionalId: number;
}
