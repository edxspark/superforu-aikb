export interface FileLoaderParams {
    type: string;
    filePath: string;
}

export function fileLoader(params: FileLoaderParams) {
    console.log("fileLoader....");
}
