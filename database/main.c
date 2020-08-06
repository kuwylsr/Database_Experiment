/*
 * test.c
 * Zhaonian Zou
 * Harbin Institute of Technology
 * Jun 22, 2011
 */

#include <stdlib.h>
#include <stdio.h>
#include "extmem.h"
/*
 * extmem.c
 * Zhaonian Zou
 * Harbin Institute of Technology
 * Jun 22, 2011
 */

Buffer *initBuffer(size_t bufSize, size_t blkSize, Buffer *buf)
{
    int i;

    buf->numIO = 0;
    buf->bufSize = bufSize;
    buf->blkSize = blkSize;
    buf->numAllBlk = bufSize / (blkSize + 1);
    buf->numFreeBlk = buf->numAllBlk;
    buf->data = (unsigned char*)malloc(bufSize * sizeof(unsigned char));

    if (!buf->data)
    {
        perror("Buffer Initialization Failed!\n");
        return NULL;
    }

    memset(buf->data, 0, bufSize * sizeof(unsigned char));
    return buf;
}

void freeBuffer(Buffer *buf)
{
    free(buf->data);
}

unsigned char *getNewBlockInBuffer(Buffer *buf)
{
    unsigned char *blkPtr;

    if (buf->numFreeBlk == 0)
    {
        perror("Buffer is full!\n");
        return NULL;
    }

    blkPtr = buf->data;

    while (blkPtr < buf->data + (buf->blkSize + 1) * buf->numAllBlk)
    {
        if (*blkPtr == BLOCK_AVAILABLE)
            break;
        else
            blkPtr += buf->blkSize + 1;
    }

    *blkPtr = BLOCK_UNAVAILABLE;
    buf->numFreeBlk--;
    return blkPtr + 1;
}

void freeBlockInBuffer(unsigned char *blk, Buffer *buf)
{
    *(blk - 1) = BLOCK_AVAILABLE;
    buf->numFreeBlk++;
}

int dropBlockOnDisk(unsigned int addr)
{
    char filename[40];

    sprintf(filename, "%d.blk", addr);

    if (remove(filename) == -1)
    {
        perror("Dropping Block Fails!\n");
        return -1;
    }

    return 0;
}

unsigned char *readBlockFromDisk(unsigned int addr, Buffer *buf)
{
    char filename[40];
    unsigned char *blkPtr, *bytePtr;
    char ch;

    if (buf->numFreeBlk == 0)
    {
        perror("Buffer Overflows!\n");
        return NULL;
    }

    blkPtr = buf->data;

    while (blkPtr < buf->data + (buf->blkSize + 1) * buf->numAllBlk)
    {
        if (*blkPtr == BLOCK_AVAILABLE)
            break;
        else
            blkPtr += buf->blkSize + 1;
    }

    sprintf(filename, "%d.blk", addr);
    FILE *fp = fopen(filename, "rb");

    if (!fp)
    {
        perror("Reading Block Failed!\n");
        return NULL;
    }

    *blkPtr = BLOCK_UNAVAILABLE;
    blkPtr++;
    bytePtr = blkPtr;
//??????????????????????????????????????????????????????
    while (bytePtr < blkPtr + buf->blkSize)
    {
        ch = fgetc(fp);
        *bytePtr = ch;
        bytePtr++;
    }

    fclose(fp);
    buf->numFreeBlk--;
    buf->numIO++;
    return blkPtr;
}

int writeBlockToDisk(unsigned char *blkPtr, unsigned int addr, Buffer *buf)
{
    char filename[40];
    unsigned char *bytePtr;

    sprintf(filename, "%d.blk", addr);
    FILE *fp = fopen(filename, "wb");

    if (!fp)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }

    for (bytePtr = blkPtr; bytePtr < blkPtr + buf->blkSize; bytePtr++)
        fputc((int)(*bytePtr), fp);

    fclose(fp);
    *(blkPtr - 1) = BLOCK_AVAILABLE;
    buf->numFreeBlk++;
    buf->numIO++;
    return 0;
}

int main()
{
    Buffer buf; /* A buffer */
    unsigned char *blk; /* A pointer to a block */
    int i = 0;

    /* Initialize the buffer */
    if (!initBuffer(20, 8, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }

    /* Get a new block in the buffer */
    blk = getNewBlockInBuffer(&buf);

    /* Fill data into the block */
    for (i = 0; i < 8; i++)
        *(blk + i) = 'a' + i;

    /* Write the block to the hard disk */
    if (writeBlockToDisk(blk, 31415926, &buf) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }

    /* Read the block from the hard disk */
    if ((blk = readBlockFromDisk(31415926, &buf)) == NULL)
    {
        perror("Reading Block Failed!\n");
        return -1;
    }

    /* Process the data in the block */
    for (i = 0; i < 8; i++)
        printf("%c", *(blk + i));

    printf("\n");
    printf("# of IO's is %d\n", buf.numIO); /* Check the number of IO's */

    return 0;
}

